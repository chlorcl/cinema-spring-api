# Cinema Spring API - Instrukcja Konfiguracji

## Opis Projektu
Cinema Spring API to aplikacja backendowa do zarządzania systemem kinowym, zbudowana przy użyciu Spring Boot. Aplikacja oferuje funkcjonalności zarządzania kinami, salami, seansami, biletami i użytkownikami.

## Wymagania Systemowe
- Java 21
- Maven
- MySQL
- Docker (opcjonalnie)

## Konfiguracja Projektu

### Klonowanie Repozytorium
```bash
git clone [adres-repozytorium]
cd cinema-spring-api
```

### Konfiguracja Bazy Danych
Aplikacja wymaga bazy danych MySQL. Możesz skonfigurować ją na dwa sposoby:

#### 1. Lokalna Instalacja MySQL
- Zainstaluj MySQL na swoim komputerze
- Utwórz bazę danych o nazwie `cinema`
- Upewnij się, że serwer MySQL działa na porcie 3306

#### 2. Użycie Docker Compose
Projekt zawiera plik `compose.yaml`, który pozwala na uruchomienie MySQL w kontenerze Docker:
```bash
docker-compose up -d
```

### Profile Aplikacji
Aplikacja posiada dwa profile konfiguracyjne:

#### Profil Development (Domyślny)
Profil używany podczas rozwoju aplikacji.

Aby uruchomić aplikację z profilem development:
```bash
mvn spring-boot:run
```
lub
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=development
```

#### Profil Production
Profil używany w środowisku produkcyjnym.

Aby uruchomić aplikację z profilem produkcyjnym:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=production
```
lub
```bash
mvn spring-boot:run -P prod
```

### Konfiguracja Właściwości Aplikacji
Aplikacja używa plików właściwości do konfiguracji:
- `application-development.properties` - dla profilu development
- `application-production.properties` - dla profilu produkcyjnego

Repozytorium zawiera plik `application-example.properties` jako szablon konfiguracji. Aby skonfigurować aplikację:

1. Skopiuj plik `application-example.properties` i utwórz pliki:
   ```bash
   cp src/main/resources/application-example.properties src/main/resources/application-development.properties
   cp src/main/resources/application-example.properties src/main/resources/application-production.properties
   ```

2. Edytuj utworzone pliki, zastępując wartości zastępcze rzeczywistymi danymi:
   - Ustaw prawidłowe dane dostępu do bazy danych
   - Wygeneruj własny klucz JWT (powinien być długim, losowym ciągiem znaków)

**Ważne:** 
- Pliki konfiguracyjne z rzeczywistymi danymi dostępowymi są ignorowane przez Git i nie będą przesyłane do repozytorium.
- Przed wdrożeniem aplikacji w środowisku produkcyjnym należy zmienić domyślne hasła i klucze JWT w plikach konfiguracyjnych.

## Budowanie Aplikacji
Aby zbudować aplikację, użyj:
```bash
mvn clean package
```

Plik JAR zostanie utworzony w katalogu `target`.

## Uruchamianie Aplikacji
Po zbudowaniu aplikacji, możesz ją uruchomić za pomocą:
```bash
java -jar target/cinemaapi-0.0.1-SNAPSHOT.jar
```

Aby określić profil podczas uruchamiania pliku JAR:
```bash
java -jar -Dspring.profiles.active=production target/cinemaapi-0.0.1-SNAPSHOT.jar
```

## Dostęp do API
- REST API: `http://localhost:8080/api`
- GraphQL API: `http://localhost:8080/graphql`
- GraphiQL (interfejs do testowania GraphQL): `http://localhost:8080/graphiql`

## Funkcjonalności
- Zarządzanie kinami
- Zarządzanie salami kinowymi
- Zarządzanie miejscami
- Zarządzanie filmami
- Zarządzanie seansami
- Zarządzanie biletami
- Zarządzanie użytkownikami i autoryzacją

## Technologie
- Spring Boot 3.4.0
- Spring Data JPA
- Spring Security
- Spring GraphQL
- JWT dla autoryzacji
- MySQL
