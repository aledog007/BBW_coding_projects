import './App.css';
import Articel from "./components/Articel.jsx";
import Overview from "./components/Overview.jsx";

// hier Ihren Namen eintragen
// Alessio Fano 
function App() {
const [price, setprice] = useState("0.00");
  const [output, setOutput] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    const price = parseFloat(price);
    console.log(price)
    setOutput(converted + "\n" + output)
  }
  return (
    <>
      <div>
          <h1>Ausgabenübersicht</h1>
          <Articel
          header="Datum"
          type="text"
          />
          <Articel
          header="Was"          
          type="text"
          />
          <Articel
          header="Preis"
          type="text"
          />
          <Articel
          type="submit"
          />
          <Overview
          title="Liste"
          //value={outputAll}
          />
          <Overview
          title="Summe"
          //value={outputAll}
          />
      </div>
    </>
  )
}

export default App
