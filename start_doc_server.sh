#!/bin/sh
# 增量启动本地doc服务器，服务器地址为http://localhost:4000
cd .verify-helper/markdown/
bundle exec jekyll serve --incremental