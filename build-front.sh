#!/bin/bash

cd WebView/site

ng build

cd ../..

cp -rf WebView/site/dist/site/* SpringBootServer/src/main/resources/static
