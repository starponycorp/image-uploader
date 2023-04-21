### Описание
Микросервис для загрузки изображений, преобразования его в нужный формат и сохранение в
хранилище. Предполагается что он должен разгрузить основной сервер веб приложения.

Сделан в первую очередь для себя и под свои нужды)

### Конфигурация

Шаблон файла конфигурации:
```yaml
configuration:
  # Настройки хранилища изображений, поле enabled может иметь значение true 
  #   только у одной из конфигурации хранилищ
  storage:
    minio:
      enabled: true
      address: "адрес сервера minio"
      accessKey: ""
      secretKey: ""
    local:
      enabled: false
      path: "media"

  formats: # Список форматов
    avatars: # Имя директорию в которую будет сохраняться изображение
      type: "webp" # Формат итогового изображение после обработки (png, jpeg, jpg, webp)
      width: 128
      height: 128
    ...
```

### Запуск
1) Загрузить готовый пакет https://github.com/starponycorp/image-uploader/releases

2) Создать файл конфигурации [application.yaml](#Конфигурация)

3) Запуск

```shell
java -jar image-uploader.jar
```

### Docker

1) Загрузка образа

```shell
docker push starponyprod/image-uploader:1.0
```

2) Создать файл конфигурации [application.yaml](#Конфигурация)

3) Создание контейнера из образа и запуск

```shell
docker run -p 8080:8080 -v {путь до файла конфигурации}:application.yaml -v {путь к директории куда будут вестись логи}:logs -v {путь к директории куда будут сохраняться изображения}:media starponycorp/image-uploader
```