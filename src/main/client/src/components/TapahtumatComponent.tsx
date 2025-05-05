import { useState } from "react";
import LuoTapahtumaComponent from "./LuoTapahtumaComponent";
import MuokkaaTapahtumaaComponent from "./MuokkaaTapahtumaaComponent";
import LuoTapahtumapaikkaComponent from "./LuoTapahtumapaikkaComponent";

export default function TapahtumatComponent() {
  const [activeTab, setActiveTab] = useState(1);

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const switchTab = (e:any) => {
    setActiveTab(parseInt(e.target.id));
  }

  return(
    <>
      <div className="container my-4">
        
        {/***** Tab navigation *****/}
        <ul className="nav nav-tabs">
          <li className="nav-item">
            <a id="1" className={"nav-link " + (activeTab === 1 ? "active" : "")} onClick={e => switchTab(e)}>Luo tapahtuma</a>
          </li>
          <li className="nav-item">
            <a id="2" className={"nav-link " + (activeTab === 2 ? "active" : "")} onClick={e => switchTab(e)}>Muokkaa tapahtumaa</a>
          </li>
          <li className="nav-item">
            <a id="3" className={"nav-link " + (activeTab === 3 ? "active" : "")} onClick={e => switchTab(e)}>Luo tapahtumapaikka</a>
          </li>
        </ul>

        
        {/***** Tab 1 - luo tapahtuma *****/}
        <div className={"container " + (activeTab === 1 ? "" : "display-none")}>
          <LuoTapahtumaComponent />
        </div>

         {/***** Tab 2 - Muokkaa tapahtumaa *****/}
         <div className={"container " + (activeTab === 2 ? "" : "display-none")}>
          <MuokkaaTapahtumaaComponent />
        </div>

        
         {/***** Tab 3 - Muokkaa tapahtumaa *****/}
         <div className={"container " + (activeTab === 3 ? "" : "display-none")}>
          <LuoTapahtumapaikkaComponent />
        </div>


      </div>
    </>
  )
}