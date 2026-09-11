export default function Home({ onEnter }: { onEnter: () => void }) {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center relative overflow-hidden animate-fade-in">
      {/* Background glowing orbs */}
      <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-cyan-500/20 rounded-full blur-[120px] animate-pulse"></div>
      <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-blue-600/20 rounded-full blur-[120px] animate-pulse" style={{ animationDelay: '2s' }}></div>

      <div className="z-10 text-center space-y-10 max-w-3xl p-12">
        <div className="space-y-4">
          <div className="inline-flex items-center justify-center p-4 bg-white/5 rounded-3xl border border-white/10 backdrop-blur-xl mb-6 shadow-2xl">
            <span className="text-5xl">🌿</span>
          </div>
          <h1 className="text-7xl md:text-8xl font-extrabold tracking-tight bg-clip-text text-transparent bg-gradient-to-br from-white via-cyan-100 to-cyan-500 drop-shadow-sm">
            Smartbite
          </h1>
          <p className="text-xl text-cyan-50/70 font-light max-w-lg mx-auto leading-relaxed">
            The autonomous Zero-Waste kitchen ecosystem. 
            <br className="hidden md:block" />
            Scan receipts, track inventory, and cook smart.
          </p>
        </div>
        
        <div className="pt-8">
          <button 
            onClick={onEnter}
            className="group relative inline-flex items-center justify-center px-10 py-5 font-bold text-white transition-all duration-500 bg-cyan-500/10 border border-cyan-400/30 rounded-full hover:bg-cyan-500/20 hover:scale-105 hover:border-cyan-300/50 focus:outline-none"
          >
            <span className="absolute inset-0 w-full h-full rounded-full bg-gradient-to-br from-cyan-400/20 to-blue-500/20 blur-md opacity-0 group-hover:opacity-100 transition-opacity duration-500"></span>
            <span className="relative flex items-center gap-3 text-lg tracking-wide">
              Enter Dashboard
              <svg className="w-6 h-6 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 7l5 5m0 0l-5 5m5-5H6"></path></svg>
            </span>
          </button>
          <p className="mt-6 text-sm text-white/30 uppercase tracking-widest font-semibold">Mock-Engine MVP Modus</p>
        </div>
      </div>
    </div>
  );
}
