import { useState } from 'react'
import { Link, NavLink } from 'react-router-dom'
import { Motion, AnimatePresence, motion } from 'framer-motion'

function IconDashboard(){ return (<svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 3h7v7H3zM14 3h7v4h-7zM14 11h7v10h-7zM3 11h7v10H3z"/></svg>) }
function IconRules(){ return (<svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c1.657 0 3-1.567 3-3.5S13.657 1 12 1 9 2.567 9 4.5 10.343 8 12 8zM6 6h.01M18 6h.01M4 21h16"/></svg>) }

export default function SidebarLayout({children}){
  const [open,setOpen] = useState(true)
  return (
    <div className="min-h-screen flex">
      <aside className={`bg-white border-r ${open? 'w-64':'w-20'} transition-all`}>
        <div className="p-4 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-10 h-10 rounded bg-gradient-to-br from-brand-500 to-brand-600 flex items-center justify-center text-white font-bold">F</div>
            {open && <div><div className="text-lg font-semibold">Findora</div><div className="text-xs text-gray-500">Underwriting</div></div>}
          </div>
          <button onClick={()=>setOpen(o=>!o)} className="text-gray-500">{open? '◀':'▶'}</button>
        </div>
        <nav className="px-2">
          <NavLink to="/" className={({isActive})=>`flex items-center gap-3 p-2 rounded hover:bg-gray-50 ${isActive? 'bg-gray-100':''}`}>
            <IconDashboard/> {open && <span>Dashboard</span>}
          </NavLink>
          <NavLink to="/underwriting" className={({isActive})=>`flex items-center gap-3 p-2 rounded hover:bg-gray-50 ${isActive? 'bg-gray-100':''}`}>
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12h6M9 16h6M9 8h6"/></svg>
            {open && <span>Evaluate</span>}
          </NavLink>
          <NavLink to="/rules" className={({isActive})=>`flex items-center gap-3 p-2 rounded hover:bg-gray-50 ${isActive? 'bg-gray-100':''}`}>
            <IconRules/> {open && <span>Rules</span>}
          </NavLink>
          <NavLink to="/results" className={({isActive})=>`flex items-center gap-3 p-2 rounded hover:bg-gray-50 ${isActive? 'bg-gray-100':''}`}>
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 17v-6a2 2 0 00-2-2H5"/><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 7h6v10h-6z"/></svg>
            {open && <span>Results</span>}
          </NavLink>
        </nav>
        <div className="mt-auto p-4 text-xs text-gray-500">
          {open && <div>© {new Date().getFullYear()} Findora</div>}
        </div>
      </aside>
      <div className="flex-1 bg-gray-50">
        <header className="bg-white border-b">
          <div className="container mx-auto px-4 py-3 flex items-center justify-between">
            <div className="text-sm text-gray-600">Underwriting Dashboard</div>
            <div className="flex items-center gap-4">
              <div className="text-sm text-gray-600">User</div>
            </div>
          </div>
        </header>
        <div className="container mx-auto px-4 py-6">
          {children}
        </div>
      </div>
    </div>
  )
}
