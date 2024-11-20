import {Message} from "./message.ts";

export interface Session {
    sessionId: string
    messages: Message[]
}

export interface SessionNaseptavac {
    sessionId: string
    messages: string
}

