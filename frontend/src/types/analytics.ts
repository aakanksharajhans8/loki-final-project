// Interface for the cleaned, structured Silver data
export interface SilverEvent {
  id: number;
  userId: string;
  sessionId: string;
  eventType: string;
  page: string;
  elementId?: string;
  eventTimestamp: string;
}

// Interface for the aggregated Gold data
export interface GoldUserSession {
  sessionId: string;
  userId: string;
  sessionStart: string;
  sessionEnd: string;
  eventCount: number;
  converted: boolean;
}