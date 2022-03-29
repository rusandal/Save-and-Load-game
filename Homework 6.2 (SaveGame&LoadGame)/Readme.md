# Сериализация и архивация 
Used: interface Serializable, FileOutputStream|FileInputStream|ZipOutputStream
## Описание
Данная программа имитирует процесс сохранения игры. Данный процесс построен на сериализации Java класса с использованием интерфейса Serializable с последущей упаковкой в архив .zip. Для создания структуры папок и файлов можно использовать [программу](https://github.com/rusandal/Save-and-Load-game/tree/master/Homework%206.1%20(Streams%20IO%2C%20Files%2C%20Serialization)/src)  
Пример сериализуемых объектов
```
GameProgress gameProgress = new GameProgress(15, 20, 30, 12.3);
GameProgress gameProgress1 = new GameProgress(16, 21, 31, 13.3);
```