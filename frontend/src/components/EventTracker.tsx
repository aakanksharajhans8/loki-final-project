import { useEffect } from 'react';
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/events';

interface EventPayload {
  userId: string;
  sessionId: string;
  event: string;
  page: string;
  metadata?: Record<string, any>;
}

export const trackEvent = (payload: EventPayload) => {
  const eventToSend = {
    ...payload,
    timestamp: new Date().toISOString(),
  };

  axios.post(API_URL, eventToSend)
    .then(() => console.log(`Event tracked: ${payload.event}`))
    .catch(error => console.error('Failed to track event:', error));
};

// This is a React component that attaches a global event listener
export const EventTracker = () => {
  useEffect(() => {
    const userId = 'user-123'; // In a real app, this would come from an auth context
    const sessionId = `session_${Date.now()}`; // Simple session ID for demo

    const handleGlobalClick = (e: MouseEvent) => {
      const targetElement = e.target as HTMLElement;
      if (['BUTTON', 'A', 'INPUT'].includes(targetElement.tagName)) {
        trackEvent({
          userId,
          sessionId,
          event: 'generic_click',
          page: window.location.pathname,
          metadata: {
            elementId: targetElement.id,
            elementType: targetElement.tagName,
            elementText: targetElement.textContent?.trim().substring(0, 50),
          },
        });
      }
    };

    window.addEventListener('click', handleGlobalClick);
    return () => window.removeEventListener('click', handleGlobalClick);
  }, []);

  return null; // This component doesn't render anything
};