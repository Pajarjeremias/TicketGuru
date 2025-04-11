import './App.css'
import { Outlet } from 'react-router-dom'

function App() {

  return (
    <>
      <nav className="navbar bg-primary" data-bs-theme="dark">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">TicketGuru4ever</a>
          <form action="/logout" method="post">
        <input type="submit" value="Sign Out" className="btn btn-sm btn-danger me-2"/>
      </form>
        </div>
        
      </nav>
      <Outlet />
    </>
  )
}

export default App
