# AA Game

A minimal, fast, and faithful recreation of the classic mobile game **aa**—shoot pins into a spinning wheel without colliding with anything already attached. Built with Java (JavaFX) and packaged with Maven.

---

## Features

- **Arcade-tight gameplay:** click/tap to fire pins; avoid collisions to clear the level  
- **Deterministic spin logic:** predictable yet tense difficulty curve  
- **Level configuration:** number of pins, initial angles, spin speed, and direction  
- **Responsive rendering:** JavaFX scene graph with CSS styling  
- **Clean project layout:** standard Maven + `src/main/java` organization

> If you’re new to the game: a central circle spins; you have **N** pins to shoot. Land every pin without touching others to win the level.

---

## 🚀 Quick Start

### Prerequisites
- **Java 17+** (Adoptium Temurin, Oracle, or OpenJDK)
- **Maven 3.8+**

### Clone
```bash
git clone https://github.com/dibahadie/AA_Game.git
cd AA_Game
```

### Run (dev)
```bash
mvn clean javafx:run
```

### Build a runnable JAR
```bash
mvn -DskipTests=true clean package
# Then:
java -jar target/AA_Game-*-runner.jar
```

---

## 🎮 Controls

- **Mouse / Trackpad / Touch:** Click (or tap) anywhere in the window to shoot the next pin.
- **R:** Restart current level (if implemented in your build).
- **Esc:** Quit (or close the window).

---

## 🔧 Configuration

Typical level knobs (check the level/config classes):

- **`pinsCount`** – total pins the player must stick  
- **`spinSpeed`** – degrees per second (may reverse or vary per level)  
- **`spinDirection`** – clockwise / counter-clockwise  
- **`initialAngles`** – pre-placed pins for challenge setups  
- **`failOnCollision`** – boolean collision rule (usually `true`)

---

## 📄 License

This repository currently doesn’t declare a license file. If you plan to reuse or distribute the code, please open an issue to clarify licensing with the maintainer.

---

## 🙌 Credits

- Original concept inspired by the mobile game **aa**  
- Implementation by **@dibahadie**

---
