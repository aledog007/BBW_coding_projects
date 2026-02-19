import { useState } from 'react';
import './App.css';
import Button from "./components/Button";
import Result from './components/Result';
import InputCHF from "./components/input";
import Picture from "./components/picture";

function App() {
  const [currency, setCurrency] = useState("0.00");
  const [output, setOutput] = useState("");

  const handleclear = () => {
    setOutput("")
  }
  const handleSubmit = (event) => {
    event.preventDefault();
    const inputAmount = parseFloat(currency);
    const converted = inputAmount * 1.1
    console.log(converted)
    setOutput(converted + "\n" + output)
  }

  return (
    <form onSubmit={handleSubmit}>
      <Picture />

      <InputCHF 
        CHF="Umrechnen von CHF: "
        name="Schweizerfranken"
        currency={currency}
        setCurrency={setCurrency}
      />

      <Button 
        type="submit"
        value="calculate"
        handleClick={handleSubmit}
      />

      <Button 
        type="button"
        value="clear"
        handleClick={handleclear}
      />

      <Result
        title="Resultat:"
        value={output}
      />
    </form>
  )
}

export default App;