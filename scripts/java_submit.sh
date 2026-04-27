#!/usr/bin/env bash
set -euo pipefail

if [ $# -ne 1 ]; then
  echo "用法: $0 path/to/File.java"
  exit 1
fi

src="$1"

if [ ! -f "$src" ]; then
  echo "文件不存在: $src"
  exit 1
fi

case "$src" in
  *.java) ;;
  *)
    echo "只支持 .java 文件: $src"
    exit 1
    ;;
esac

python3 - "$src" <<'PY'
import re
import sys
from pathlib import Path

src = Path(sys.argv[1]).resolve()
text = src.read_text(encoding="utf-8")

stem = src.stem
target = src.with_name("Main.java")

patterns = [
    rf'\bpublic\s+class\s+{re.escape(stem)}\b',
    rf'\bclass\s+{re.escape(stem)}\b',
]

new_text = text
count = 0
for i, pattern in enumerate(patterns):
    replacement = 'public class Main' if i == 0 else 'class Main'
    new_text, count = re.subn(pattern, replacement, text, count=1)
    if count > 0:
        break

if count == 0:
    # 兜底：匹配第一个 class 声明
    new_text, count = re.subn(r'\bpublic\s+class\s+\w+\b', 'public class Main', text, count=1)
    if count == 0:
        new_text, count = re.subn(r'\bclass\s+\w+\b', 'class Main', text, count=1)

if count == 0:
    raise SystemExit("未找到可替换的类声明，无法生成 Main.java")

target.write_text(new_text, encoding="utf-8")
print(f"已生成: {target}")
PY