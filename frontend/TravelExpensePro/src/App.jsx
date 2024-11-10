import { useState } from 'react'
import ErrorBox  from './components/ErrorBox'
import Header from './components/Header'
// import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
  
      <Header />
      <ErrorBox message="weee" />
      </>
  )
}

export default App
