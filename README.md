# 🏋️ GymPulse — Capstone Project

Piattaforma Full-Stack per la gestione smart dell'esperienza in palestra.

🔗 **Frontend Repository:** [gympulse-frontend](https://github.com/FabryPesa23/gympulse-frontend)

---

## 🧱 Architettura

Il progetto è diviso in due repository:

- **Backend** — Java + Spring Boot (questo repository)
- **Frontend** — React + Vite ([gympulse-frontend](https://github.com/FabryPesa23/gympulse-frontend))

---

## 🚀 Tech Stack

### Backend
- ☕ Java 25 + Spring Boot 4
- 🔐 Spring Security + JWT
- 🗄️ JPA / Hibernate + PostgreSQL
- ☁️ Cloudinary (upload immagini)
- 📧 Mailgun (email transazionali)
- 📖 Swagger / OpenAPI (documentazione API)

### Frontend
- ⚛️ React + Vite
- 🎨 React Bootstrap
- 🧭 React Router
- 🌐 Fetch API nativa

---

## ✨ Funzionalità

- 🔑 Autenticazione con JWT (register e login)
- 📅 Gestione corsi, categorie e time slot
- 🎟️ Sistema di prenotazione con waitlist automatica
- 👥 Crowd monitoring live per zone della palestra
- 👤 Profilo utente con upload foto
- 📨 Email di conferma prenotazione e notifica waitlist
- 📖 Documentazione API con Swagger UI
- 🛡️ Gestione ruoli ADMIN e USER

---

## ⚙️ Avvio in locale

### Backend
1. Clona il repository
2. Crea il database: `CREATE DATABASE gympulse_db;`
3. Crea il file `env.properties` nella root con le variabili d'ambiente
4. Avvia con IntelliJ o `./mvnw spring-boot:run`
5. Il server parte su `http://localhost:8080`

### Frontend
1. Clona il repository frontend
2. Installa le dipendenze: `npm install`
3. Avvia: `npm run dev`
4. Il client parte su `http://localhost:5173`

---

## 🔒 Variabili d'ambiente

Crea un file `env.properties` nella root del backend:

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

> ⚠️ Non committare mai questo file! È nel `.gitignore`.

---

## 📖 Documentazione API

Avvia il progetto e vai su:
`http://localhost:8080/swagger-ui/index.html`

---

## 🗃️ Schema Database

- 👤 `users` — Utenti registrati
- 📚 `course_categories` — Categorie dei corsi
- 🏃 `courses` — Corsi disponibili
- 🕐 `time_slots` — Orari dei corsi
- 🎟️ `bookings` — Prenotazioni utenti
- ⏳ `waitlist` — Lista d'attesa
- 🏠 `gym_zones` — Zone della palestra
- 📊 `crowd_reports` — Segnalazioni affollamento

---

## 👨‍💻 Autore

**Fabrizio Pesaresi** — Epicode Capstone 2026

[![GitHub](https://img.shields.io/badge/GitHub-FabryPesa23-black?logo=github)](https://github.com/FabryPesa23)