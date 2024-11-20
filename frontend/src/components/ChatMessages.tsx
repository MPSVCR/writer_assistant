import {Message} from "../model/message.ts";
import MarkdownDisplay from "./MarkdownDisplay.tsx";
import BotLogo from "./../assets/bot.png"

type Props = {
    messages: Message[];
    promptPending?: boolean
}

const ChatMessages
    = ({ messages, promptPending = false }: Props) => {

    return (
        <div className="w-full p-4">
            {messages.map((message, index) => (
                <div key={index} className="flex items-center w-full gap-4">
                    {message.type !== 'prompt' && (
                        <img src={BotLogo} alt="Prompt icon" className="w-6 h-6 scale-x-[-1]"/>
                    )}
                    <div
                        className={`p-3 my-2 rounded-lg shadow-md text-gray-900 w-full ${
                            message.type === 'prompt'
                                ? 'bg-primary text-white self-end ml-20'
                                : 'bg-gray-200 self-start mr-20'
                        }`}
                    >
                        <MarkdownDisplay markdownText={message.text}/>
                    </div>
                </div>
            ))}
            {promptPending && (
                <div className="flex space-x-1 bg-gray-200 mr-20 p-3 my-2 rounded-lg shadow-md w-fit">
                    <div className="w-2.5 h-2.5 bg-gray-500 rounded-full animate-bounce"
                         style={{animationDelay: "0ms"}}></div>
                    <div className="w-2.5 h-2.5 bg-gray-500 rounded-full animate-bounce"
                         style={{animationDelay: "300ms"}}></div>
                    <div className="w-2.5 h-2.5 bg-gray-500 rounded-full animate-bounce"
                         style={{animationDelay: "500ms"}}></div>
                </div>
            )}
        </div>
    );
};

export default ChatMessages;