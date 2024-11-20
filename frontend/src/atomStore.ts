import { atom } from 'jotai';
import {Session, SessionNaseptavac} from "./model/session.ts";
import { v4 as uuidv4 } from 'uuid';

export const chatAtom = atom<Session>({sessionId: uuidv4(), messages: []})
export const trainedChatAtom = atom<Session>({sessionId: uuidv4(), messages: []})

export const naseptavacAtom = atom<SessionNaseptavac>({sessionId: uuidv4(), messages: ''})