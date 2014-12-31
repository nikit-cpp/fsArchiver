Building
========
Проект собирается на jdk7+  
From project's folder `fsArchiver`:  
`mvn package`

Windows launch example
======================
`cd target`  
`java -Dfile.encoding=Cp866 -jar fsArchiver.jar -i "C:\\вх папка" -o "C:\\вых папка" -s 1 -n 8 -p`

Linux launch example
====================
`cd target`  
`java -jar fsArchiver.jar -i /home/nik/music/ -o "/home/nik/tmp/Безымянная папка" -s 1 -n 8 -p`

Get help:
=========
`cd target`  
`java -jar fsArchiver.jar -h`

Features list
=============
* сравнение списков игнорируя порядок элементов `ListAssert.assertEquals` WorkerTest.java:88
* использование Apache Commons CLI, преобразование аргументов в `int` и `File`
* "fat" jar
* многопоточность через `ExecutorService`, `Callable<Void>`

Интеграция Groovy в Eclipse
===========================
0. Ставим Groovy по инструкции с [офф. сайта](http://groovy.codehaus.org/Installing+Groovy), прописываем GROOVY_HOME
1. Выбираем Help -> Eclipse Marketplace, ищем и ставим Groovy
![](https://img-fotki.yandex.ru/get/16103/165433899.1/0_132075_4dd844da_orig)  
**Снимаем галочки как на скрине**, ибо несколько сайтов (Update Site) умерло   
![](https://img-fotki.yandex.ru/get/15593/165433899.1/0_132077_98dc2a65_orig)
2. Чтобы убрать красный крестик с проекта, нужно поставить m2e connector for groovy:  
Help -> Install New Software, вставляем http://dist.springsource.org/release/GRECLIPSE/e4.4  
Снова выбираем галочки как на скриншоте  
![](https://img-fotki.yandex.ru/get/15591/165433899.1/0_132076_9dd2ae0c_orig)
**После установки ПКМ по проекту, Maven -> Update**

Решение проблем
===============
1. Вместо Build Path-папок src/main/java, src/main/groovy видна папка src, нет настроек Build Path
--------------------------------------------------------------------------------------------------
Решение
-------
удалить папку .metadata из workspace, перезапустить Eclipse, импортировать заново все проекты
