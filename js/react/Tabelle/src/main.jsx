import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import TabeleExample from './tabeleExample.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
    <TabeleExample />
  </StrictMode>,
)
