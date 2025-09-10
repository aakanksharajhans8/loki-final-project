import { useState, useEffect } from 'react';
import axios from 'axios';
import { EventTracker } from './components/EventTracker';
import type { SilverEvent, GoldUserSession } from './types/analytics';
import './App.css';

const SILVER_API_URL = 'http://localhost:8080/api/events/silver';
const GOLD_API_URL = 'http://localhost:8080/api/analytics/sessions';

function App() {
  const [silverEvents, setSilverEvents] = useState<SilverEvent[]>([]);
  const [goldSessions, setGoldSessions] = useState<GoldUserSession[]>([]);
  const [error, setError] = useState('');

  const fetchData = async () => {
    try {
      const [silverRes, goldRes] = await Promise.all([
        axios.get<SilverEvent[]>(SILVER_API_URL),
        axios.get<GoldUserSession[]>(GOLD_API_URL)
      ]);
      setSilverEvents(silverRes.data);
      setGoldSessions(goldRes.data);
    } catch (err) {
      setError('Failed to fetch analytics data. Is the backend running?');
      console.error(err);
    }
  };

  useEffect(() => {
    fetchData(); // Fetch initial data
    const interval = setInterval(fetchData, 10000); // Refresh data every 10 seconds
    return () => clearInterval(interval); // Cleanup on component unmount
  }, []);

  return (
    <>
      <EventTracker /> {/* This component handles sending events */}
      <div className="container">
        <header>
          <h1>Insurance Portal Analytics</h1>
          <p>Click around the page on the buttons below. Your raw events are being collected, processed in the background (Bronze → Silver → Gold), and displayed here. The data will auto-refresh.</p>
          <div className="button-container">
             <button id="view_quote_btn">View Quote</button>
             <button id="buy_now_btn">Buy Now</button>
             <a href="#" id="learn_more_link">Learn More</a>
          </div>
        </header>

        {error && <p className="error-message">{error}</p>}
        
        <main className="analytics-layout">
          <div className="table-container">
            <h2>🥇 Gold Layer: Aggregated User Sessions</h2>
            <table>
              <thead>
                <tr>
                  <th>Session ID</th>
                  <th>User ID</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Event Count</th>
                  <th>Converted?</th>
                </tr>
              </thead>
              <tbody>
                {goldSessions.map(session => (
                  <tr key={session.sessionId}>
                    <td>{session.sessionId}</td>
                    <td>{session.userId}</td>
                    <td>{new Date(session.sessionStart).toLocaleTimeString()}</td>
                    <td>{new Date(session.sessionEnd).toLocaleTimeString()}</td>
                    <td>{session.eventCount}</td>
                    <td>{session.converted ? '✅' : '❌'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className="table-container">
            <h2>🥈 Silver Layer: Cleaned Events</h2>
            <table>
              <thead>
                <tr>
                  <th>User ID</th>
                  <th>Event Type</th>
                  <th>Page</th>
                  <th>Element ID</th>
                  <th>Timestamp</th>
                </tr>
              </thead>
              <tbody>
                {silverEvents.map(event => (
                  <tr key={event.id}>
                    <td>{event.userId}</td>
                    <td>{event.eventType}</td>
                    <td>{event.page}</td>
                    <td>{event.elementId || 'N/A'}</td>
                    <td>{new Date(event.eventTimestamp).toLocaleTimeString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </main>
      </div>
    </>
  );
}

export default App;