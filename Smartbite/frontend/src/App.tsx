import { useState } from 'react';
import Scanner from './components/Scanner';
import Dashboard from './components/Dashboard';
import SmartCoach from './components/SmartCoach';
import DevPanel from './components/DevPanel';
import Home from './components/Home';

function App() {
  const [currentScreen, setCurrentScreen] = useState<'home' | 'dashboard'>('home');
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handleUpdate = () => {
    setRefreshTrigger(prev => prev + 1);
  };

  if (currentScreen === 'home') {
    return <Home onEnter={() => setCurrentScreen('dashboard')} />;
  }

  return (
    <div className="min-h-screen p-4 md:p-8 animate-fade-in relative overflow-hidden">
      {/* Background Ambience for Dashboard */}
      <div className="fixed top-0 right-0 w-[800px] h-[800px] bg-cyan-900/10 rounded-full blur-[150px] pointer-events-none"></div>
      <div className="fixed bottom-0 left-0 w-[600px] h-[600px] bg-blue-900/10 rounded-full blur-[150px] pointer-events-none"></div>

      <div className="max-w-7xl mx-auto space-y-8 relative z-10">
        <header className="flex flex-col md:flex-row justify-between items-start md:items-center mt-2 mb-10 gap-4">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 bg-white/5 rounded-2xl border border-white/10 flex items-center justify-center backdrop-blur-md">
              <span className="text-2xl">🌿</span>
            </div>
            <div>
              <h1 className="text-3xl md:text-4xl font-extrabold bg-clip-text text-transparent bg-gradient-to-r from-white to-cyan-200 drop-shadow-sm pb-1">
                Smartbite
              </h1>
              <p className="text-cyan-100/50 mt-0.5 text-xs uppercase tracking-widest font-semibold">Mock-Engine MVP</p>
            </div>
          </div>
          <button 
            onClick={() => setCurrentScreen('home')}
            className="text-white/60 hover:text-white transition-all duration-300 text-sm px-5 py-2.5 rounded-full border border-white/10 hover:bg-white/10 backdrop-blur-sm flex items-center gap-2"
          >
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
            Back to Home
          </button>
        </header>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
          <div className="lg:col-span-4 space-y-6">
            <Scanner onScanComplete={handleUpdate} />
            <SmartCoach />
          </div>
          <div className="lg:col-span-8 space-y-6">
            <Dashboard refreshTrigger={refreshTrigger} />
            <DevPanel refreshTrigger={refreshTrigger} onIoTTriggered={handleUpdate} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
