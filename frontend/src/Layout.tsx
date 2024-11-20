import Logo from './assets/oksystem-logo.png'
import Tabs from "./components/Tabs.tsx";
import {Outlet} from "react-router-dom";

export default function Layout() {
    return (
        <div className="flex flex-col h-screen">
            <div className="sticky top-0 z-10 bg-white py-10 overflow-hidden">
                <div className="mx-auto max-w-7xl px-6 lg:px-8">
                    <div className="flex flex-col justify-start gap-8 mx-10 max-w-3xl lg:mx-0">
                        <img src={Logo} alt="" className="w-28"/>
                        <h2 className="text-4xl font-semibold tracking-tight text-gray-900 sm:text-4xl">
                            Pomocník pro zápisy z šetření
                            <small className="ml-4 text-2xl text-gray-400">technologické demo</small>
                        </h2>
                    </div>
                </div>
            </div>
            <Tabs/>
            <div className="flex-1 overflow-y-auto p-4">
            <Outlet />
            </div>
        </div>
    )
}

