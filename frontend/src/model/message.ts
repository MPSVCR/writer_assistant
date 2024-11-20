export interface Message {
    type: 'prompt' | 'answer';
    text: string;
}