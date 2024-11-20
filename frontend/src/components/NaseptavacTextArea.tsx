import {useState, useEffect, useRef, KeyboardEvent} from "react";
import {getData} from "../api/naseptavacApi.ts";
import {useAtom} from "jotai/index";
import {naseptavacAtom} from "../atomStore.ts";
import BotNaseptavac from "../assets/botNaseptavac.png";
import {PlusCircleIcon} from "@heroicons/react/24/outline";
import {addSpaceIfNeeded, setHintProperly} from "../utils/pomocnikUtils.ts";

type Props = {
    model: string
}

function NaseptavacTextArea({model}: Props) {
    const [session, setSession] = useAtom(naseptavacAtom)
    const [promptText, setPromptText] = useState<string>('')
    const [hints, setHints] = useState<string[]>()
    const [, setSelectedHint] = useState<string>('')
    const [divPosition, setDivPosition] = useState({top: 0, left: 0});
    const textareaRef = useRef<HTMLTextAreaElement>(null)
    const hintRef = useRef<HTMLDivElement>(null)
    const [showHint, setShowHint] = useState<boolean>(false)
    const [promptPending, setPromptPending] = useState(false)

    const handleClickOutside = (event: MouseEvent) => {
        if (hintRef.current && event.target instanceof Node && !hintRef.current.contains(event.target)) {
            setShowHint(false)
        }
    };

    useEffect(() => {
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    const handleKeyDown = (event: KeyboardEvent<HTMLTextAreaElement>) => {
        if (event.ctrlKey && event.key === 'Enter') {
            handleSend()
        }
    };

    const handleChange = () => {
        if (!textareaRef.current) return
        const {selectionStart, selectionEnd} = textareaRef.current;
        const currentText = textareaRef.current.value;
        setSession({...session, messages: currentText})

        const newText =
            currentText.slice(0, selectionStart) +
            '***' +
            currentText.slice(selectionEnd);

        setPromptText(newText);
    };

    const handleSend = async () => {
        setPromptPending(true)
        if (promptText !== '') {
            try {
                const result = await getData(session.sessionId, promptText, model);
                setHints(result);
                setPromptPending(false)
                setShowHint(true)
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        } else {
            setPromptPending(false)
        }
    };

    const handleChooseHint = (hint: string) => {
        setSelectedHint(hint)
        const updatedText = promptText.replace('***', addSpaceIfNeeded(hint, session.messages));
        setPromptText(updatedText);
        setSession(prev => ({...prev, messages: updatedText}))
        setShowHint(false)
    }

    useEffect(() => {
        if (textareaRef.current) {
            const textarea = textareaRef.current;
            const {selectionStart} = textarea;

            if (selectionStart !== null) {
                const textBeforeCursor = textarea.value.substring(0, selectionStart);

                const tempSpan = document.createElement("span");
                tempSpan.style.position = "absolute";
                tempSpan.style.visibility = "hidden";
                tempSpan.style.whiteSpace = "pre-wrap";
                tempSpan.textContent = textBeforeCursor;

                document.body.appendChild(tempSpan);

                const rect = textarea.getBoundingClientRect();
                const spanRect = tempSpan.getBoundingClientRect();

                setDivPosition({
                    top: rect.top + spanRect.height + window.scrollY,
                    left: rect.left + spanRect.width + window.scrollX,
                });


                document.body.removeChild(tempSpan);
            }
        }
    }, [hints]);

    return (
        <div className="flex flex-col size-full gap-2 justify-between h-full">
            {showHint && hints && <div style={{
                position: 'absolute',
                top: divPosition.top + 20,
                left: "50%",
                background: 'white',
                padding: '8px',
                borderRadius: '4px'
            }}
                className="-translate-x-1/2 shadow-md border rounded-lg border-gray-300 text-sm text-gray-900 p-4 min-w-80 w-content"
            ref={hintRef}
            >
                <ul className="flex flex-col gap-2">
                    {hints && hints.map((hint, index) =>
                        hint &&
                        <li key={index} onClick={() => handleChooseHint(hint)}
                            className="flex items-center gap-1">
                            <PlusCircleIcon className="h-4 w-4"/>
                            <p className="flex items-center gap-1.5 px-2 py-1.5 shadow-sm bg-green-100 rounded-lg cursor-pointer wrap">{setHintProperly(hint, session.messages)}</p>
                        </li>)}
                </ul>
            </div>
            }
            <div className="h-full">
            <textarea
                id="description"
                name="description"
                placeholder="Začněte psát..."
                autoFocus={true}
                className="w-full h-5/6 p-4 bg-gray-100 rounded-lg shadow-inner border border-gray-300 cursor-text text-sm resize-none outline-none"
                value={session.messages}
                onChange={handleChange}
                ref={textareaRef}
                onKeyDown={handleKeyDown}
            />
                {!promptPending && !session.messages &&
                    <p className="text-sm text-gray-500 pb-4">Napiště alespoň jeden znak</p>}
            </div>
            <button
                onClick={handleSend}
                className="flex items-center justify-center gap-2 w-full px-4 py-2.5 rounded-lg bg-primary text-white hover:bg-gray-800 focus:outline-none disabled:bg-gray-500"
                disabled={promptPending || !session.messages}
            >
                {promptPending && (
                    <div className="flex space-x-1 mr-4 w-fit">
                        <div className="w-2.5 h-2.5 bg-current opacity-75 rounded-full animate-bounce"
                             style={{animationDelay: "0ms"}}></div>
                        <div className="w-2.5 h-2.5 bg-current opacity-75 rounded-full animate-bounce"
                             style={{animationDelay: "300ms"}}></div>
                        <div className="w-2.5 h-2.5 bg-current opacity-75 rounded-full animate-bounce"
                             style={{animationDelay: "500ms"}}></div>
                    </div>
                )}
                {!promptPending && <><p>Spustit našeptávání</p></>}
                <img src={BotNaseptavac} alt="Prompt icon" className="h-7 scale-x-[-1]"/>
            </button>
        </div>
    )
}

export default NaseptavacTextArea
