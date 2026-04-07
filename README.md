# 🌉 Bay Area Trail 

A Java-based simulation game inspired by *The Oregon Trail*, where players navigate from San Jose to San Francisco while managing resources, making strategic decisions, and surviving the challenges of modern exploration.

Players must manage the following resources:
- **💰 Cash**
- **☕ Coffee**
- **😊 Team Morale**
- **📈 Daily Active Users**
- **🔋 Laptop Battery**

---

## Quick Start
### Prerequisites
- Java 17+
- Maven

## Setup
1. Clone the repository:
```bash
git clone https://github.com/ChristianaRazafindrasoa/bay-area-trail.git
cd bay-area-trail
```

2. Copy file:
```bash
cp .env.example .env
```

3. Configure environment variable:
```dotenv
MAPBOX_SECRET_KEY=your_api_key_here
```

## Running the App
```bash
mvn clean compile exec:java
```

## Testing
Run tests using:
```bash
mvn test
```

## AI Usage
AI tools were used to assist with:
- API integration
- Debugging
- Documentation generation

---

# Design Notes

## Game Loop
1. Start the day
2. Choose an action (search, explore, rest, etc.)
3. Consume resources
4. Trigger events (success, failure, random outcomes)
5. Update state
6. Repeat until success or failure

## API Integration
- Uses Mapbox Search API for location-based queries
- Finds nearby coffee shops and points of interest

## Error Handling
The game handles:
- Network failures
- Missing API keys
- Empty responses
- Rate limits

If API calls fail:
- The app continues running
- Errors are handled gracefully
- Gameplay proceeds without crashing (mock-like fallback)

## Future Improvements
- Add UI (web or mobile)
- More random events
- Smarter resource balancing
- Additional APIs (e.g., Yelp, OpenAI)





