import {
    createBrowserRouter,
    RouterProvider
} from "react-router-dom";
import "./index.css";
import App from "./App.tsx";
import ErrorPage from "./pages/ErrorPage.tsx";
import ChatPage from "./pages/ChatPage.tsx";
import NaseptavacPage from "./pages/NaseptavacPage.tsx";
import {chatAtom, trainedChatAtom} from "./atomStore.ts";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                index: true,
                path: "/chat",
                element: <ChatPage model={"generator_v1"} atom={chatAtom}/>,
            },
            {
                path: "/naseptavac",
                element: <NaseptavacPage model={"naseptavac_v1"}/>,
            },
            {
                path: "/chat-v2",
                element: <ChatPage model={"generator_v2"} atom={trainedChatAtom}/>,
            },
            // {
            //     path: "/naseptavac-v2",
            //     element: <NaseptavacPage model={"naseptavac_v2"}/>,
            // },
        ],
    },
]);

export function Router() {
    return <RouterProvider router={router}/>;
}