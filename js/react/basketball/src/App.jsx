import { useState } from 'react';
import './App.css';

function App() {
  const [guestName , setGuestName] = useState("eagles");
  const [homeName , setHomeName] = useState("wasps");
  const [guestPoints , setGuestPoints] = useState(0);
  const [homePoints , setHomePoints] = useState(0);

  //Array of History texts
  const [history, setHistory] = useState(['Game starts']);
  
  const onAddHomePoints = (newPoints) => {
    setHomePoints(newPoints);
    history.push("Home throws, new points: " + newPoints)
    if(newPoints >= 12){
      history.push("Home Wins")
    }
  }

  const onAddGuestPoints = (newPoints) => {
    setHomePoints(newPoints);
    history.push("Guest throws, new points: " + newPoints)
    if(newPoints >= 12){
      history.push("Guest Wins")
    }
  }

  return (
    <>
      <h1>Basketball</h1>
      <section>
        <aside>
          <Team 
            label="Home Team"
            name={homeName}
            setName={setHomeName}
            points={homePoints}
            setPoints={onAddHomePoints}
          />
        </aside>
        <aside>
          <History />
        </aside>
        <aside>
          <Team
            label="Guest Team"
            name={guestName}
            setName={setGuestName}
            points={guestPoints}
            setPoints={onAddGuestPoints} 
            />
        </aside>
      </section>
    </>
  )
}

export default App
