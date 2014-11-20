Building
========
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