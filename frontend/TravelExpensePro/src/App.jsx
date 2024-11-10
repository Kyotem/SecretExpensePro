import { useState } from 'react'
import ViewClaimPage from './pages/ViewClaimPage/ViewClaimPage'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
  <ViewClaimPage />
      </>
  )
}

export default App
