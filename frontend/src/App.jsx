import { Routes, Route } from 'react-router-dom'
import SidebarLayout from './components/SidebarLayout'
import Home from './pages/Home'
import Underwriting from './pages/Underwriting'
import Rules from './pages/Rules'
import Results from './pages/Results'

export default function App(){
  return (
    <SidebarLayout>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/underwriting" element={<Underwriting/>} />
        <Route path="/rules" element={<Rules/>} />
        <Route path="/results" element={<Results/>} />
      </Routes>
    </SidebarLayout>
  )
}
