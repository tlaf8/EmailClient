from random import sample, randint

names = [
    "John", "Jane", "Alice", "Bob", "Charlie", "David", "Eva", "Frank",
    "Grace", "Henry", "Isabel", "Liam", "Sophia", "Daniel", "Olivia",
    "Ethan", "Mia", "Lucas", "Emma", "Noah", "Ava", "James", "Amelia",
    "Benjamin", "Mason", "Ella", "Oliver", "Emily", "Jack", "Lily",
    "Matthew", "Zoe", "Samuel", "Nina", "William", "Hannah", "Sebastian"
]

USER_COUNT = 100

with open('sql_dump.sql', 'w+') as sql_dump:
    sql_dump.write('CREATE DATABASE IF NOT EXISTS `AcmePlexUserInfo`;\nUSE `AcmePlexUserInfo`;\nCREATE TABLE IF NOT EXISTS `users` (\n\t`id` INT NOT NULL AUTO_INCREMENT,\n\t`name` VARCHAR(255) NOT NULL,\n\t`email` VARCHAR(255) NOT NULL UNIQUE,\n\t`mailing_list` TINYINT(1) NOT NULL,\n\t`registered` TINYINT(1) NOT NULL,\n\tPRIMARY KEY (`id`)\n)\nENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n')
    sql_dump.write('INSERT INTO users (name, email, mailing_list, registered) VALUES\n')
    for i in range(USER_COUNT):
        name = sample(names, 2)
        email = f'{name[0]}.{name[1]}{randint(1000, 9999)}@gmail.com'.lower()
        registered = randint(0, 1)
        mailing_list = randint(0, 1) if registered else 0
        if i < USER_COUNT - 1:
            sql_dump.write(f'\t("{name[0]} {name[1]}", "{email}", {mailing_list}, {registered}),\n')
        else:
            sql_dump.write(f'\t("{name[0]} {name[1]}", "{email}", {mailing_list}, {registered});')