export interface Device {
  id?: number;
  description: string;
  address: string;
  maxConsumption: number;
  userId: number;
}

export interface Measurement {
  id?: number;
  deviceId?: number;
  consumedEnergy: number;
  timestamp: number;
}

export interface User {
  id?: number;
  name: string;
  password: string;
  role: Role;
}

export enum Role {
  ADMIN = "ADMIN",
  CLIENT = "CLIENT",
  NONE = "NONE"
}

export interface LoginResponse {
  token: string;
  userId: number;
  role: Role;
}

export interface Message {
  senderId: string;
  receiverId: string;
  message: string;
  status: Status;
}

export enum Status {
  TYPING = 'TYPING',
  MESSAGE = 'MESSAGE',
  SEEN = 'SEEN'
}

export interface UserMessagePair {
  [userId: string]: StrippedMessage[]
}

export interface StrippedMessage {
  message: string;
  isReceived: boolean;
  seen?: boolean
}

export interface StrippedClient {
  id: string;
  name: string;
  isTyping?: boolean;
}
