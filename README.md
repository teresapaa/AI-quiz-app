# 🎓 AI Quiz Master

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)
![AI](https://img.shields.io/badge/AI-Ollama%20%2F%20Llama%203.2-green.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**An intelligent quiz generation platform powered by AI that dynamically creates personalized quizzes for educators and students.**

[Features](#-features) • [Tech Stack](#-tech-stack) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture)

</div>

---

## 📋 Overview

AI Quiz Master is a JavaFX desktop application developed as a group project at Queensland University of Technology. It leverages local AI models (Ollama with Llama 3.2) to automatically generate quiz questions based on user-specified topics, difficulty levels, and educational standards. The application provides a complete quiz management system with user authentication, score tracking, and flexible quiz modes.

### 🎯 Key Highlights

- **AI-Powered Generation**: Utilizes Llama 3.2 LLM via Ollama for intelligent quiz creation
- **Multiple Quiz Modes**: Practice mode (untimed) and Exam mode (timed)
- **Full CRUD Operations**: Create, read, update, and delete quizzes
- **User Management**: Complete authentication system with secure password hashing
- **Score Tracking**: Historical performance tracking with detailed analytics
- **Export Capabilities**: Download quizzes as PDF or text files for offline use
- **Clean Architecture**: Implements DAO pattern with SQLite persistence

---

## ✨ Features

### 🔐 User Authentication
- **Registration & Login**: Secure user account creation with email validation
- **Password Security**: SHA-256 hashing with validation requirements (min 8 chars, letters + digits)
- **Session Management**: Persistent user sessions with logout functionality

### 📝 Quiz Creation & Management
- **AI-Generated Questions**: Specify topic, difficulty, and number of questions
- **Customizable Parameters**:
  - Subject selection
  - Difficulty levels (Easy, Medium, Hard)
  - Year level targeting (customizable by country)
  - Question quantity (5, 10, 15, 20, 25)
- **Visibility Controls**: Public or private quiz sharing
- **Quiz Editing**: Modify existing quizzes and questions

### 🎮 Quiz Taking Experience
- **Practice Mode**: Relaxed, self-paced learning with no time limit
- **Exam Mode**: Timed quizzes with automatic timer calculation based on difficulty
- **Instant Feedback**: Immediate scoring and answer validation
- **Progress Tracking**: View all past attempts with scores and timestamps

### 📊 Analytics & Export
- **Score History**: Detailed performance tracking over time
- **Export Options**: Download quizzes in PDF or plain text format
- **Attempt Analysis**: Review selected answers and correct responses

---

## 🛠️ Tech Stack

### Core Technologies
- **Java 17+**: Modern Java with records and pattern matching support
- **JavaFX 21**: Rich desktop UI framework with FXML
- **SQLite**: Embedded database for data persistence
- **Ollama4j**: Java client for Ollama AI integration
- **Llama 3.2**: Meta's latest open-source LLM

### Libraries & Dependencies
```xml
<!-- Key dependencies -->
- javafx-controls & javafx-fxml
- ollama4j (AI integration)
- org.json (JSON parsing)
- jackson-databind (object mapping)
- apache-commons-lang3
```

### Design Patterns
- **DAO Pattern**: Abstracted data access layer (`IUserDAO`, `IQuizDAO`, `IQuestionDAO`)
- **Singleton Pattern**: Database connection management (`SqliteConnection`)
- **MVC Architecture**: Separation of Models, Views (FXML), and Controllers
- **Factory Pattern**: Quiz and question generation

---

## 📦 Installation

### Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version
   ```

2. **Ollama** (for AI quiz generation)
   ```bash
   # Install Ollama from https://ollama.ai
   ollama pull llama3.2
   ```

3. **Maven** (for dependency management)
   ```bash
   mvn -version
   ```

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/teresapaa/AI-quiz-app.git
   cd AI-quiz-app
   ```

2. **Ensure Ollama is running**
   ```bash
   # Start Ollama service (default: http://localhost:11434)
   ollama serve
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn javafx:run
   ```

---

## 🎯 Usage

### Getting Started

1. **Create an Account**
   - Launch the application
   - Click "Register" on the welcome screen
   - Enter username, email, and a secure password
   - Password must be 8+ characters with letters and numbers

2. **Generate Your First Quiz**
   - Log in with your credentials
   - Click "Create Quiz"
   - Fill in quiz parameters:
     - Quiz name
     - Subject (e.g., Mathematics, Science, History)
     - Topic (e.g., Algebra, Photosynthesis, World War II)
     - Number of questions (5-25)
     - Difficulty level
     - Year level and country
   - Click "Generate" and wait for AI to create questions

3. **Take a Quiz**
   - Select "Practice Mode" (no timer) or "Exam Mode" (timed)
   - Answer multiple-choice questions
   - Submit to view your score and correct answers

4. **Review Performance**
   - Access "Score History" to view all past attempts
   - Filter by quiz, date, or score
   - Track improvement over time

---

## 🏗️ Architecture

### Project Structure

```
src/
├── main/
│   ├── java/com/example/quizapp/
│   │   ├── Controllers/          # JavaFX controllers
│   │   ├── Models/                # Data models and DAOs
│   │   │   ├── User.java
│   │   │   ├── Quiz.java
│   │   │   ├── Question.java
│   │   │   ├── QuizAttempt.java
│   │   │   ├── SqliteUserDAO.java
│   │   │   ├── SqliteQuizDAO.java
│   │   │   ├── SqliteQuestionDAO.java
│   │   │   ├── OllamaResponse.java  # AI integration
│   │   │   └── SqliteConnection.java
│   │   ├── utils/                 # Utility classes
│   │   │   ├── AuthManager.java
│   │   │   ├── QuizManager.java
│   │   │   ├── SceneManager.java
│   │   │   └── AlertManager.java
│   │   ├── data/
│   │   │   └── DatabaseSeeder.java
│   │   └── Main.java
│   └── resources/
│       └── com/example/
│           ├── *.fxml             # UI layouts
│           └── UI-design.css      # Styling
└── test/
    └── java/com/example/quizapp/  # JUnit tests
```

### Database Schema

```sql
-- Users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userName VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL  -- SHA-256 hashed
);

-- Quizzes table
CREATE TABLE quizzes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR NOT NULL,
    subject VARCHAR NOT NULL,
    topic VARCHAR NOT NULL,
    timer INTEGER NOT NULL,
    difficulty VARCHAR NOT NULL,
    yearLevel VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    visibility VARCHAR,
    creatorID INTEGER NOT NULL,
    FOREIGN KEY (creatorID) REFERENCES users(id)
);

-- Questions table
CREATE TABLE questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    quizID INTEGER NOT NULL,
    questionText VARCHAR NOT NULL,
    correctAnswer VARCHAR NOT NULL,
    incorrectAnswer1 VARCHAR NOT NULL,
    incorrectAnswer2 VARCHAR NOT NULL,
    incorrectAnswer3 VARCHAR NOT NULL,
    FOREIGN KEY (quizID) REFERENCES quizzes(id)
);

-- Quiz attempts table
CREATE TABLE quizAttempts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    quizID INTEGER NOT NULL,
    userID INTEGER NOT NULL,
    score DOUBLE NOT NULL,
    attemptTime INTEGER NOT NULL,
    selectedAnswers TEXT,
    FOREIGN KEY (quizID) REFERENCES quizzes(id),
    FOREIGN KEY (userID) REFERENCES users(id)
);
```

### AI Integration

The application uses **Ollama4j** to communicate with locally-running Llama 3.2:

```java
// Simplified example from OllamaResponse.java
OllamaAPI ollamaAPI = new OllamaAPI("http://localhost:11434/");
ollamaAPI.setRequestTimeoutSeconds(120);

OllamaResult result = ollamaAPI.generate(
    "llama3.2", 
    prompt, 
    false, 
    new OptionsBuilder().build()
);
```

Prompts are structured to generate JSON responses with questions and answers.

---

## 🧪 Testing

The project includes JUnit test coverage for core functionality:

```bash
mvn test
```

Test files:
- `UserTest.java` - User model validation
- `DashboardTest.java` - Dashboard functionality
- Mock DAOs for unit testing without database dependencies

---

## 🚀 Future Enhancements

This project was developed under tight academic deadlines. Potential improvements include:

- **Cloud-based AI**: Integration with OpenAI or Anthropic APIs for broader accessibility
- **Multi-language Support**: Internationalization for global users
- **Collaborative Features**: Quiz sharing and leaderboards
- **Mobile Version**: React Native or Flutter implementation
- **Advanced Analytics**: ML-based learning recommendations
- **Question Bank**: Reusable question repository

---


<div align="center">

**⭐ If you find this project interesting, please consider giving it a star!**

</div>
