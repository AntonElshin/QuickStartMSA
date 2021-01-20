			Инструкция по инициализации и обновлении  базы данных под  "Платформу SOA"
	
	Предварительные условия и требования.

1. Для инициализации базы данных Вам необходимо установить один из поддерживаемых серверов баз данных:
	* MS SQL Server 2000
	* MS SQL Server 2005
	* Oracle 10g
	* Oracle 11g
	* DB2 9.7
	* PostgreSQL 9.5

При установке  MS SQL Server 200X убедитесь, что выбран case insensitive collation. 
Это важно! Платформа не поддерживает работу с case sensitive collations.

2. Для инициализации данных Вам необходимо знать пароль администратора БД, он должен обладать ролью db_owner. Если база данных для инициализации данных создается вручную, то для нее нужно указать следующие настройки:
	* abort tran on log full
	* select into/bulkcopy/pllsort
	* trunc log on chkpt
	* ddl in tran
	* allow nulls by default
   Перед инициаилизацией необходимо удалить все пользовательские данные:
	* типы, 
	* таблицы, 
	* значения по умолчанию.
	* процедуры

3. В Oracle 10g или 11g должна быть создана база данных средствами Database Configuration Assistant.

4. В DB2 необходимо при помощи внешнего инструмента (например Control Center) создать БД с размером страницы по умолчанию 8kb, для уже существующей БД можно создать TABLESPACE работы со станицами 8kb.

5. Для СУДБ, отличных от заявленных Вам понадобится JDBC драйвер для Вашей СУБД. Скопируйте его в папку lib.

6. Перед обновлением базы данных рекомендуется сделать полный бекап всех данных.
	
7. Пользователь на Oracle должен иметь права на execute DBMS_LOCK (если пользователь создается Администратором БД, то он должен иметь право развавать эту привилегию на объект).	
	
	Порядок действий

Для инициализации базы данных под "Платформу SOA" запустите файл dm.bat  
Формат комадной строки:

	dm.bat [commands] [options]
	

Скрипт инициализации понимает следующие команды:

	-d или drop - удаляет существующую базу данных  
	-c или create - создает базу данных и пользователя 
	-i или init - инициализирует базу данных (создает таблицы, индексы и т.п.)
	-u или update - обновляет структуру БД до текущей версии. 
	-v или validate - проверяет соответствие структуры БД текущей версии. Эта команда действует по умолчанию.
		
Скрипту инициализации необходимо передавать следующие опции:
			
	--driver=<driverClassname> - задает имя класса - JDBC драйвера для доступа к БД (можно не указывать для поддерживаемых)
	--url=<url> - задает JDBC URL для доступа к БД
	--database=<databaseName> - задает имя базы данных для создания, удаления, проверки или инициализации (для Oracle имя базы данных = имя схемы = имя пользователя)
	--username=<username> - задает имя пользователя, который будет создан и которого следует использовать для соединения с БД 		
	--password=<password> - задает пароль пользователя, по умолчанию: совпадает с именем пользователя
	--admin=<username> - задет имя суперпользователя, у которого есть права на создание баз данных, таблиц и т.п.
	--adminPassword=<password> - задает пароль суперпользователя.
	--sqlFile=<file> - задает имя файла, в который будет записан SQL скрипт для выполнения. БД изменена не будет.
	--sqlFileEncoding=<encoding> - кодировка для файла с SQL скриптом, по умолчанию используется кодировка UTF-8 	
	--logLevel=<all|finest|finer|fine|info|warning|severe|off> - задает уровень логирования
	--logFile=<file> - задает имя файла для сохранения логов. По умолчанию логи выводятся на консоль.
	--locale=<locale> - задает альфа-код локали для установки дистрибутивных данные (по умолчанию ru)
	-Dmastername=<sys> - имя схемы для MSSQL, необходимо указать если имя отлично от sys, в базе master(например -Dmastername=dbo)
	-Ddefaulttablespace=<users> - табличное пространство по умолчанию для Oracle, необходимо указать, если имя отлично от users (например -Ddefaulttablespace=myuser)
	-Dtemptablespace=<temp> - табличное пространство временных сегментов для Oracle, необходимо указать, если имя отлично от temp (например -Dtemptablespace=mytemp)
	-D<propertyName>=<propertyValue> - задают дополнительные параметры, необходимые для инициализации или обновления БД

В случае успешной работы скрипта, в конце Вы получите соответствующее сообщение.
Иначе Вы получите сообщение об ошибке.


При миграции с версии 5.0.1.48 и более ранних, Вам потребуется указать два дополнительных параметра

	productPrefix - системное имя Вашего проекта, например -DproductPrefix=front
		
	metadataUrl - путь по которому расположены метаданные Вашего проекта, например -DmetadataUrl=c:/metadata
	
	
	
	Примеры.
	
Oracle:
	
	Создать и проинициализировать базу данных "с нуля", если БД уже существует, удалить ее:

	dm.bat -d -c -i --url=jdbc:oracle:thin:@OracleHost:1521:OracleSID --database=example1 --username=example --password=examplepass  --admin=system --adminPassword=secret
	
	Обновить существующую БД до текущей версии:
	
	dm.bat -u --url=jdbc:oracle:thin:@OracleHost:1521:OracleSID --database=example1 --username=example --password=examplepass

* Обратите внимание, что в Oracle имя пользователя и имя БД есть суть одно и тоже, 
поэтому опция username не имеет силы, программа создаст пользователя с именем заданным опцией database.

* Обратие внимание что, для Orcale для создания базы логин admin должен обладать административными правами, 
	но не быть равным SYS 

MSSQL:

	Создать и проинициализировать базу данных "с нуля", если БД уже существует, удалить ее:
	
	dm.bat -d -c -i --url=jdbc:jtds:sqlserver://MSSQLHost:1433 --database=example1 --username=exowner1 --password=exownerpass1 --admin=sa --adminPassword=secret  -Dmastername=dbo
	
	Обновить существующую БД до текущей версии:

	dm.bat -u --url=jdbc:jtds:sqlserver://MSSQLHost:1433/example1 --database=example1 --username=sa --password=secret 	

* Обратите внимание, что обновление БД MS SQL Server необходимо выполнять под администратором БД, 
иначе будет выдано сообщение об ошибке.  

DB2:

	Проинициализировать базу данных "с нуля"*:
	
	dm.bat -i --url=jdbc:db2://DB2Host/example1 --database=example1 --username=systemUser --password=sysUserPass --admin=systemUser --adminPassword=sysUserPass
	
	Обновить существующую БД до текущей версии:
	
	dm.bat -u --url=jdbc:db2://DB2Host/example1 --database=example1 --username=systemUser --password=sysUserPass
	
* Перед инициализацией БД необходимо создать БД средствами управления DB2.
** Обратите внимание, что создание/обновление БД DB2 необходимо выполнять под существующим пользователем операционной системы, на которой установлен сервер БД.
	
PostgreSQL:

	Создать и проинициализировать базу данных "с нуля", если БД уже существует, удалить ее:
	
	dm.bat -d -c -i --url=jdbc:postgresql://PostgreSQLHost:5432/postgres --database=example1 --username=exowner1 --password=exownerpass1 --admin=postgres --adminPassword=secret --driver=org.postgresql.Driver

	Проинициализировать базу данных "с нуля"*:

	dm.bat -i --url=jdbc:postgresql://PostgreSQLHost:5432 --database=example1 --username=exowner1 --password=exownerpass1 --driver=org.postgresql.Driver

	Обновить существующую БД до текущей версии:

	dm.bat -u --url=jdbc:postgresql://PostgreSQLHost:5432 --database=example1 --username=exowner1 --password=exownerpass1 --driver=org.postgresql.Driver

* Обратите внимание, что наименование базы данных и логин пользователя должны быть заданы в нижнем регистре
