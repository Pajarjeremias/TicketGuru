import { useState } from "react";
import SelaaTapahtumienLippujaComponent from "./SelaaTapahtumienLippujaComponent";
import TapahtumanMyynnitComponent from "./TapahtumanMyynnitComponent";
import TapahtumanMyynnitTyypeittainComponent from "./TapahtumanMyynnitTyypeittainComponent";


export default function MyyntiraportitComponent() {
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
            <a id="1" className={"nav-link " + (activeTab === 1 ? "active" : "")} onClick={e => switchTab(e)}>Selaa tapahtumien lippuja</a>
          </li>
          <li className="nav-item">
            <a id="2" className={"nav-link " + (activeTab === 2 ? "active" : "")} onClick={e => switchTab(e)}>Tapahtuman myynnit</a>
          </li>
          <li className="nav-item">
            <a id="3" className={"nav-link " + (activeTab === 3 ? "active" : "")} onClick={e => switchTab(e)}>Tapahtuman myynnit lipputyypeittäin</a>
          </li>
        </ul>

        
        {/***** Tab 1 - Selaa tapahtumien lippuja *****/}
        <div className={"container " + (activeTab === 1 ? "" : "display-none")}>
          <SelaaTapahtumienLippujaComponent />
        </div>

         {/***** Tab 2 - Tapahtuman myynnit *****/}
         <div className={"container " + (activeTab === 2 ? "" : "display-none")}>
          <TapahtumanMyynnitComponent />
        </div>

        {/***** Tab 3 - Tapahtuman myynnit *****/}
        <div className={"container " + (activeTab === 3 ? "" : "display-none")}>
          <TapahtumanMyynnitTyypeittainComponent />
        </div>

      </div>
    </>
  )
}