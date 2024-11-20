import {useEffect, useRef, useState, KeyboardEvent} from 'react';
import {ArrowUpIcon} from "@heroicons/react/24/solid";
import {Message} from "../model/message.ts";

type Props = {
    addMessage: (message: Message) => void
    promptPending?: boolean
}

const ChatInput = ({addMessage, promptPending = false}: Props) => {
    const [message, setMessage] = useState('');
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    const handleSend = () => {
        if (message.trim()) {
            addMessage({type: "prompt", text: message})
            setMessage('');
        }
    };

    const handleKeyDown = (e: KeyboardEvent<HTMLTextAreaElement>) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            handleSend();
        }
    };

    useEffect(() => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto'; // Reset height
            const scrollHeight = textareaRef.current.scrollHeight;
            const maxHeight = 5 * parseFloat(getComputedStyle(textareaRef.current).lineHeight!); // 5 lines max height
            textareaRef.current.style.height = `${Math.min(scrollHeight, maxHeight)}px`;
            textareaRef.current.style.overflowY = scrollHeight > maxHeight ? 'auto' : 'hidden';
        }
    }, [message]);

    useEffect(() => {
        textareaRef.current?.scrollIntoView({behavior: "smooth"})
    }, [promptPending]);

    return (
        <div className="flex items-center px-4 py-2 bg-gray-100 rounded-full shadow-inner border border-gray-300 mx-4">
            {/*<button className="p-2 focus:outline-none">*/}
            {/*    <PaperClipIcon className="h-5 w-5 text-gray-500" />*/}
            {/*</button>*/}

            <textarea
                ref={textareaRef}
                className="flex-grow mx-2 bg-transparent text-sm resize-none outline-none overflow-hidden"
                placeholder="Napište zadání…"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyDown={handleKeyDown}
                rows={1}
                disabled={promptPending}
            />

            <button
                onClick={handleSend}
                className="p-2 rounded-full bg-primary text-white hover:bg-gray-800 focus:outline-none"
                disabled={promptPending}
            >
                <ArrowUpIcon className="h-5 w-5"/>
            </button>
        </div>
    );
};

export default ChatInput;
