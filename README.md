# 📅 Описание проекта
## QaseProject — это проект автоматизации тестирования, в рамках которого реализовано покрытие автотестами Qase.
#### [Qase](https://qase.io/ "Qase - Test management platform") - система управления тест-кейсами, которая позволяет эффективно управлять тестами и проектами, обеспечивая удобный интерфейс для создания, редактирования и управления тестовыми сценариями

---
# 📊 Отчеты для данного проекта : [LINK](https://romanivanov-atom.github.io/QaseProject) 

---

# 📈 Статус покрытия автотестами функций Qase
## 🔑 Вход в систему
- [x] Валидные данные
- [x] Невалидные данные
## 📂 Проект
- [x] Создание проекта: Легко создавайте новые проекты.
- [x] Изменение проекта: Редактируйте существующие проекты.
- [ ] Архивация/разархивация проекта: Удобно архивируйте или восстанавливайте проекты.
- [ ] Поиск проектов: Быстрый поиск по всем проектам.
- [ ] Сортировка проектов: Упорядочивайте проекты по различным критериям.
- [ ] Фильтрация проектов: Фильтруйте проекты по заданным параметрам.
- [x] Удаление проекта: Удаляйте ненужные проекты.
## 🧪 Тест-кейсы
- [x] Создание тест-кейса
- [x] Редактирование тест-кейса
- [ ] Клонирование тест-кейса
- [x] Удаление тест-кейса
## 📁 Тестовые наборы (Test Suites)
- [x] Создание сьюты
- [x] Редактирование сьюты
- [ ] Клонирование сьюты
- [x] Удаление сьюты
## 🏃 Test-run
- [ ] Создание test-run'a
- [ ] Редактирование test-run'a (переименование, удаление тест-кейсов в run'e)
- [ ] Выполнение test-run'a
- [ ] Повторное выполнение test-run'a
- [ ] Share test-run'a
- [ ] Удаление test-run'a
## 📋 Test-plan
- [ ] Создание тест плана
- [ ] Редактирование тест плана
- [ ] Удаление тест плана

---

## 🚀 Начало работы
### После подтягивания проекта из Git необходимо перейти в папку **resources** и выполнить следующие действия:
1. Переименуйте файл config.properties.TEMPLATE в config.properties
2. В файле config.properties замените заглушки на свои креденшнлс (qase.api.token, username, password)

---

## ⚙️ Параметры запуска
1) Добавляем безголовной запуск
> - Dmbrowser=headless   -- запуск браузера в 'безголовном' режиме
 
2) Выбор браузера для запуска
> - Dbrowser=chrome   -- Браузер Google Chrome - браузер по умолчанию. Если параметр не выбран - запускается через хром
> - Dbrowser=edge   -- Браузер Edge

3) Для запуска тестов через git actions необходимо добавить 3 секрета в 'Repository secrets' Git Hub'a:
> - USERNAME_FOR_GITHUB
> - PASSWORD_FOR_GITHUB
> - API_TOKEN_FOR_GITHUB
