# ConnectionString fuer MySQL

`CommonDbConnectionFactory.createConnection(...)` erwartet als `connectionString`
eine JDBC-URL. Bei MySQL hat sie normalerweise diese Form:

```text
jdbc:mysql://<host>:<port>/<datenbank>
```

Fuer einen MySQL-Server, der auf demselben Rechner laeuft, auf dem auch die
Anwendung gestartet wird, kann die URL zum Beispiel so aussehen:

```text
jdbc:mysql://localhost:3306/trusky
```

Die Bestandteile bedeuten:

- `jdbc:mysql` waehlt den JDBC-Treiber fuer MySQL.
- `localhost` bezeichnet den lokalen Rechner. Alternativ kann hier eine
  IP-Adresse oder ein Hostname stehen.
- `3306` ist der uebliche MySQL-Port. Wurde der Server anders konfiguriert,
  muss hier dessen Port eingetragen werden.
- `trusky` ist der Name der Datenbank, zu der die Verbindung aufgebaut wird.
  Die Datenbank muss auf dem Server bereits existieren.

Der Benutzername und das Passwort gehoeren **nicht** in diese URL. Sie werden
als separate Argumente uebergeben:

```java
CommonDbConnection connection = connectionFactory.createConnection(
        ECommonDatabaseType.MYSQL,
        "jdbc:mysql://localhost:3306/trusky",
        "appuser",
        "secret");
```

In diesem Beispiel muss MySQL lokal laufen, die Datenbank `trusky` enthalten
und dem Benutzer `appuser` den Zugriff darauf erlauben. Ausserdem muss der
MySQL-JDBC-Treiber zur Laufzeit im Klassenpfad verfuegbar sein. Die Factory
laedt den fuer `ECommonDatabaseType.MYSQL` konfigurierten Treiber und reicht
URL, Benutzername und Passwort an JDBC weiter.

Verbindungsoptionen koennen bei Bedarf an die URL angehaengt werden, zum
Beispiel:

```text
jdbc:mysql://localhost:3306/trusky?useSSL=false
```

Optionen und ihre Unterstuetzung haengen von der verwendeten Version des
MySQL-JDBC-Treibers und der Serverkonfiguration ab.
