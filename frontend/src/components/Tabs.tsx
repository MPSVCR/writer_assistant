/*
  This example requires some changes to your config:

  ```
  // tailwind.config.js
  module.exports = {
    // ...
    plugins: [
      // ...
      require('@tailwindcss/forms'),
    ],
  }
  ```
*/
import {NavLink} from "react-router-dom";

const tabs = [
    {name: 'Chat', to: '/chat'},
    {name: 'Našeptávač', to: '/naseptavac'},
    {name: 'Trénovaný chat', to: '/chat-v2'},
    // {name: 'Trénovaný našeptávač', to: '/naseptavac-v2'},
]

export default function Tabs() {
    return (
        <div className="w-full sm:mx-2">
            <div className="sm:hidden">
                <label htmlFor="tabs" className="sr-only">
                    Select a tab
                </label>
            </div>
            <div className="w-full hidden sm:block">
                <div className="w-full border-b border-gray-200">
                    <nav aria-label="Tabs" className="-mb-px flex w-full">
                        {tabs.map((tab) => (
                            <NavLink
                                key={tab.name}
                                to={tab.to}
                                className={({isActive}) => `w-full border-b-2 px-1 py-4 text-center text-sm font-medium text-gray-500
                             ${isActive ? 'border-primary text-gray-800' : 'hover:border-primary-light hover:text-gray-800'}`}
                            >
                                {tab.name}
                            </NavLink>
                        ))}
                    </nav>
                </div>
            </div>
        </div>
    )
}
