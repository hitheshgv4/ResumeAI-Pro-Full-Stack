# ResumeAI Pro — Build. Match. Get Hired.

A full-stack resume and job-search workspace inspired by the supplied screenshots. The UI uses a dark navy sidebar, warm amber active states, light grey workspace, white cards, and a live resume preview.

## Included

- React 18 + Vite responsive frontend
- Java 17 + Spring Boot 3.4 REST API
- MySQL 8 database via Docker Compose
- BCrypt password hashing and JWT authentication
- User-scoped resume and application REST endpoints
- Resume editor, live A4 preview, PDF export, resume import from text, GitHub public repository import
- ATS checklist scoring, job-description keyword comparison, tailored resume draft, cover letter drafting, interview practice prompts, sample job matching
- Application pipeline (Applied / Interview / Offer / Rejected) and analytics dashboard
- Local browser persistence so the frontend remains usable in demo mode when the API is not running

## Requirements

- Node.js 20 or newer and npm
- Java 17 or newer
- Maven 3.9+
- Docker Desktop (recommended for MySQL) or a local MySQL 8 server

## Start the database

From the project root:

```bash
docker compose up -d mysql
```

This creates database `resumeai` and exposes MySQL on port `3306`. The development defaults are username `root`, password `root`. Change these for any real deployment.

## Start the backend

Open a terminal:

```bash
cd backend
mvn spring-boot:run
```

The API starts at `http://localhost:8080`. Health check: `http://localhost:8080/api/health`.

To use a different MySQL password, set environment variables before starting the backend:

PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:JWT_SECRET="a-long-random-secret-of-at-least-32-characters"
mvn spring-boot:run
```

## Start the frontend

Open a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the URL printed by Vite (normally `http://localhost:5173`). The frontend expects the backend at `http://localhost:8080/api`. To change it, copy `frontend/.env.example` to `frontend/.env` and set `VITE_API_URL`.

## First use

1. Register an account using your name, email, and a password of at least six characters.
2. Sign in and open **Resume Builder**.
3. Add your contact details, summary, education, experience, skills, projects, and certifications.
4. Save the resume. The live preview updates while you type; use **Export PDF** to download it.
5. Use ATS Score and JD Match to review keyword coverage. Add job applications to the tracker.

## Important implementation notes

- Authentication and resume/application API persistence use the Spring Boot + MySQL backend when it is running. The browser also keeps a local working copy for continuity and offline/demo use.
- The ATS score is a transparent checklist heuristic, not a score from a specific employer's ATS. JD Match measures keyword overlap and is not a hiring prediction.
- Cover-letter and tailored-resume drafting use local templates and resume/job-description context. Interview prompts and sample job profiles are built-in starter content. They do not call a hosted generative-AI model, scrape job boards, or claim live vacancies. To add true generative AI, connect a provider from the backend using a server-side API key; never put provider secrets in the React app.
- GitHub import reads public repositories from the GitHub public API. LinkedIn does not permit general profile scraping; paste exported profile text instead.
- Change the default database password and JWT secret before deploying. Configure production CORS, HTTPS, backups, rate limiting, and account recovery before public release.

## Main API routes

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Create account |
| POST | `/api/auth/login` | Authenticate and receive JWT |
| GET | `/api/health` | API health check |
| GET | `/api/resumes` | List signed-in user's resumes |
| POST | `/api/resumes` | Create/update a resume |
| DELETE | `/api/resumes/{id}` | Delete own resume |
| GET | `/api/applications` | List signed-in user's applications |
| POST | `/api/applications` | Create/update an application |
| DELETE | `/api/applications/{id}` | Delete own application |

All routes except `/api/auth/**` and `/api/health` require `Authorization: Bearer <JWT>`.
