#!/bin/bash
curl -d '{val:123}' -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8080/mendel-all/debug/postTest	
