#TiJavaLib

## Alexel
Набор утилит и функций для работы с таблицами и данными в талбицах

##TiUtils
Набор небольших библиотек и модулей.

####PropertyService
Использование файла .properties для установления свойств при запуске программы. Файл должен располагаться около jar файла программы. 
Режимы работы:
- `Global properties file` - использование единого файла, даже в подключенных библиотеках, если они поддерживают использование глобального файла свойств. Файл задается:        PropertiesService.setGlobalPropertyFileName("FileName"). При этом используются методы: getGlobalProperty(), setGlobalProperty.

- `Individual properties file` - при считывании свойства указывается имя файла, откуда считывать свойство. При этом используются методы: getProperty(), setProperty.

##TiSerialService

Успешно тестировался до скоростей 3Мбод. На 4Мбод либо FTDI, либо Windows драйвер резко сбрасывает скорость.

##### Структура 
````
aplication   Application protocolimplements
                        |
comm|--implem      Protocol implements
    |                   |
    |--core        Procotol core
    |                   |
    |--dev          Device  ()
````