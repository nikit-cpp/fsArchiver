Windows launch example
======================
`java -jar fsArchiver.jar -i "C:\\вх папка" -o "C:\\вых папка" -s 1 -n 8`

Linux launch example
====================
`java -jar fsArchiver.jar -i /home/nik/music/ -o "/home/nik/tmp/Безымянная папка" -s 1 -n 8`

Features list
=============
* сравнение списков игнорируя порядок элементов `ListAssert.assertEquals` WorkerTest.java:88
* использование Apache Commons CLI, преобразование аргументов в int и File
* "fat" jar
* многопоточность через ExecutorService, Callable<Void>