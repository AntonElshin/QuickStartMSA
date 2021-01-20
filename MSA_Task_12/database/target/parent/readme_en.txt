1.	To initialize data DB administrator password and db_owner role should be available.
If the initializing DB is created manually the following settings should be pointed out:
	* abort tran on log full
	* select into/bulkcopy/pllsort
	* trunc log on chkpt
	* ddl in tran
	* allow nulls by default
Delete all user data prior to initialization:
	* types, 
	* tables, 
	* default values,
	* procedures

2. In Oracle 10g or 11g DB must be created by Database Configuration Assistant means.

3. Complete all data back-up should be performed prior to update.
	
4. User Oracle user should have right to execute DBMS_LOCK (if the user is created by DB Administrator there should be the right to apply the privilege on the object).	

Scenario

Launch dm.bat  to initialize DB
Command line format:

	dm.bat [commands] [options]
	

Ini script is run by following commands:
•	-d or drop – deletes actual DB  
•	-c or create – creates DB and user 
•	-i or init – initializes DB (creates tables, indexes)
•	-u or update – updates DB structure to actual version 
•	-v or validate – matches DB structure to actual version. This is a default command
		
Ini script needs to get following options:
			
•	--driver=<driverClassname> - applies class name - JDBC driver for DB access (if applicable)
•	Exception for Sybase ASE 12.5: mandatory parameter, if the installation is launched by dm.bat, point out --driver=com.sybase.jdbc2.jdbc.SybDriver
•	--url=<url> - applies JDBC URL for DB access
•	--database=<databaseName> - applies DB name to create, delete,  check and initialization (for Oracle DB name = scheme name = user name)
•	--username=<username> - applies user name which shall be created and used for DB connection 		
•	--password=<password> - applies user default password: matches user name
•	--admin=<username> - applies superuser name, hich has rights to create tables DBs etc
•	--adminPassword=<password> -applies superuser password
•	--sqlFile=<file> - applies filename which shall include, SQL script for execution. DB will not change.
•	--sqlFileEncoding=<encoding> - SQL scripted file codification, UTF-8 by default
•	--logLevel=<all|finest|finer|fine|info|warning|severe|off> - loggin level settler
•	--logFile=<file> - applies file name for log storage. Logs are moved to console by default
•	--locale=<locale> - applies localization alpha-code for distributive data installation (ru by default)
•	-Dmastername=<sys> - MSSQL scheme name, is the name is different than sys, in master(e.g. -Dmastername=dbo)
•	-D<propertyName>=<propertyValue> - applies optinal parameters for DB update or initialization
•	--oldSybase – if the DB to update to 6.03.09
•	For Sybase when DB created (c-command) few additional parameters should be set
•	--dataDevice=<dataDeviceName> - Storage Database Device  to store new created DB data
•	--dataDeviceSize=<dataDeviceSize> - Data size on Storage Database Device of DB created (minimum parameter value 1024m)
•	--logDevice=<logDeviceName> - Storage Database Device name for new created DB logs storage
•	--logDeviceSize=<dataDeviceSize> - Data size on Storage Database Device of DB created (minimum parameter value 100m)

In case of successful script completion you will get an appropriate notice 
Or error message if otherwise

	Examples.
	
Oracle:
	
	Create and initialize fresh DB, if DB is there – delete it:

	dm.bat -d -c -i --url=jdbc:oracle:thin:@OracleHost:1521:OracleSID --database=example1 --username=example --password=examplepass --admin=system --adminPassword=secret
	
	DB Update to actual version:
	
	dm.bat -u --url=jdbc:oracle:thin:@OracleHost:1521:OracleSID --database=example1 --username=example --password=examplepass

* Please not Oracle user name and DB name is the same, hence username has no value, the program creates a user named by database option.

* Please note in order to create Oracle DB admin login should have administrator rights, 
	But not equal to SYS 
