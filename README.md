# 🏋️ GymPulse — Backend

Piattaforma per la gestione smart dell'esperienza in palestra.

---

## 🚀 Tech Stack

- ☕ Java 25 + Spring Boot 4
- 🔐 Spring Security + JWT
- 🗄️ JPA / Hibernate + PostgreSQL
- ☁️ Cloudinary (upload immagini)
- 📧 Mailgun (email transazionali)
- 📖 Swagger / OpenAPI (documentazione API)

---

## ✨ Funzionalità

- 🔑 Autenticazione con JWT (register e login)
- 📅 Gestione corsi, categorie e time slot
- 🎟️ Sistema di prenotazione con waitlist automatica
- 👥 Crowd monitoring live per zone della palestra
- 👤 Profilo utente con upload foto
- 📨 Email di conferma prenotazione e notifica waitlist
- 📖 Documentazione API con Swagger UI

---

## ⚙️ Avvio in locale

1. Clona il repository
2. Crea il database: `CREATE DATABASE gympulse_db;`
3. Crea il file `env.properties` nella root con le variabili d'ambiente
4. Avvia con IntelliJ o `./mvnw spring-boot:run`
5. Il server parte su `http://localhost:8080`

---

## 🔒 Variabili d'ambiente

Crea un file `env.properties` nella root:

```properties
PORT=8080
DB_PORT=5432
DB_NAME=gympulse_db
DB_USERNAME=postgres
DB_PASSWORD=tuapassword
CLOUDINARY_NAME=tuocloudname
CLOUDINARY_API_KEY=tuaapikey
CLOUDINARY_API_SECRET=tuoapisecret
MAILGUN_API_KEY=tuamailgunkey
MAILGUN_DOMAIN=tuodominio.mailgun.org
JWT_SECRET=tuosecret
JWT_EXPIRATION=86400000
```

⚠️ Non committare mai questo file! È nel `.gitignore`.

---

## 📖 Documentazione API

Avvia il progetto e vai su:
`http://localhost:8080/swagger-ui/index.html`

---

## 👨‍💻 Autore

**Fabrizio Pesaresi** — Epicode Capstone 2026