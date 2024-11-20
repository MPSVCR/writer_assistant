import ChatInput from "../components/ChatInput.tsx";
import ChatMessages from "../components/ChatMessages.tsx";
import {PrimitiveAtom, useAtom} from "jotai";
import {Message} from "../model/message.ts";
import {fetchChatApi} from "../api/chatApi.ts";
import {useState} from "react";
import {Session} from "../model/session.ts";

type Props = {
    atom: PrimitiveAtom<Session>
    model: string
}

const ChatPage = ({atom, model}: Props) => {
    const [session, setSession] = useAtom(atom)
    const [promptPending, setPromptPending] = useState(false)

    const handleAddMessage = async (message: Message) => {
        setSession(prev => ({...prev, messages: [...prev.messages, message]}))
        setPromptPending(true)
        const response = await fetchChatApi(session.sessionId, message.text, model)
        setPromptPending(false)
        const responseMessage: Message = {type: "answer", text: response}
        setSession(prev => ({...prev, messages: [...prev.messages, responseMessage]}))
    }

    return (
        <div className="h-full flex flex-col justify-between">
            <ChatMessages messages={session.messages} promptPending={promptPending} />
            <ChatInput addMessage={handleAddMessage} promptPending={promptPending} />
        </div>
    )
}

export default ChatPage