import { useState } from "react";
import TarkastaLippuComponent from "./TarkastaLippuComponent";
import MyyLippuComponent from "./MyyLippuComponent";

export default function MainComponent() {
  const [activeTab, setActiveTab] = useState(1);

  const switchTab = () => {
    if (activeTab === 1) {
      setActiveTab(2);
    } else {
      setActiveTab(1);
    }
  }

  return(
    <>
      <div className="container my-4">
        
        {/***** Tab navigation *****/}
        <ul className="nav nav-tabs">
          <li className="nav-item">
            <a className={"nav-link " + (activeTab === 1 ? "active" : "")} onClick={switchTab}>Tarkista lippu</a>
          </li>
          <li className="nav-item">
            <a className={"nav-link " + (activeTab === 2 ? "active" : "")} onClick={switchTab}>Myy lippuja</a>
          </li>
        </ul>

        {/***** Tab 1 - Tarkasta lippu *****/}
        <div className={"container " + (activeTab === 1 ? "" : "display-none")}>
          <TarkastaLippuComponent />
        </div>
     

        {/***** Tab 2 - Myy lippu *****/}
        <div className={"container " + (activeTab === 2 ? "" : "display-none")}>
          <MyyLippuComponent />
        </div>

      </div>
    </>
  )
}