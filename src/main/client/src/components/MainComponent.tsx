import { useState } from "react";
import TarkastaLippuComponent from "./TarkastaLippuComponent";
import MyyLippuComponentUusi from "./MyyLippuComponentUusi";
import MyyntiraportitComponent from "./MyyntiraportitComponent";
import TapahtumatComponent from "./TapahtumatComponent";
import TapahtumanLipputyyppiComponentUusi from "./TapahtumanLipputyyppiComponentUusi";
import LipputyyppienHallintaComponent from "./LipputyyppienHallintaComponent";


export default function MainComponent() {
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
            <a id="1" className={"nav-link " + (activeTab === 1 ? "active" : "")} onClick={e => switchTab(e)}>Myy lippuja</a>
          </li>
          <li className="nav-item">
            <a id="2" className={"nav-link " + (activeTab === 2 ? "active" : "")} onClick={e => switchTab(e)}>Tarkista lippu</a>
          </li>
          <li className="nav-item">
            <a id="3" className={"nav-link " + (activeTab === 3 ? "active" : "")} onClick={e => switchTab(e)}>Tapahtumat</a>
          </li> 
          <li className="nav-item">
            <a id="4" className={"nav-link " + (activeTab === 4 ? "active" : "")} onClick={e => switchTab(e)}>Lipputyyppien hallinta</a>
          </li>
          <li className="nav-item">
            <a id="5" className={"nav-link " + (activeTab === 5 ? "active" : "")} onClick={e => switchTab(e)}>Tapahtumien lipputyypit</a>
          </li>
          <li className="nav-item">
            <a id="6" className={"nav-link " + (activeTab === 6 ? "active" : "")} onClick={e => switchTab(e)}>Myyntiraportit</a>
          </li>
          
        </ul>
     
        {/***** Tab 1 - Myy lippu *****/}
        <div className={"container " + (activeTab === 1 ? "" : "display-none")}>
          <MyyLippuComponentUusi />
        </div>

        {/***** Tab 2 - Tarkasta lippu *****/}
        <div className={"container " + (activeTab === 2 ? "" : "display-none")}>
          <TarkastaLippuComponent />
        </div>

        {/***** Tab 3 - Luo tapahtuma *****/}
        <div className={"container " + (activeTab === 3 ? "" : "display-none")}>
          <TapahtumatComponent />
        </div>

        {/***** Tab 4 - Tapahtuman lipputyypit *****/}
        <div className={"container " + (activeTab === 4 ? "" : "display-none")}>
          <LipputyyppienHallintaComponent />
        </div>
        
        {/***** Tab 5 - Tapahtuman lipputyypit uusi *****/}
        <div className={"container " + (activeTab === 5 ? "" : "display-none")}>
          <TapahtumanLipputyyppiComponentUusi />
        </div>

        {/***** Tab 6 - Myyntiraportit *****/}
        <div className={"container " + (activeTab === 6 ? "" : "display-none")}>
          <MyyntiraportitComponent />
        </div>

        

      </div>
    </>
  )
}