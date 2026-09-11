import { useEffect, useState } from 'react';

type Product = {
  id: string;
  productName: string;
};

export default function DevPanel({ onIoTTriggered, refreshTrigger }: { onIoTTriggered: () => void, refreshTrigger: number }) {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/inventory')
      .then(res => res.json())
      .then(data => setProducts(data || []))
      .catch(console.error);
  }, [refreshTrigger]);

  const simulateIoTDrop = async (id: string) => {
    try {
      await fetch(`http://localhost:8080/api/inventory/${id}`, { method: 'DELETE' });
      onIoTTriggered();
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div className="glass-card bg-[#050505]/90 border-red-900/30 relative overflow-hidden group">
      {/* Scan lines effect */}
      <div className="absolute inset-0 pointer-events-none bg-[linear-gradient(rgba(255,0,0,0.02)_1px,transparent_1px)] bg-[size:100%_4px] mix-blend-screen"></div>
      
      <div className="flex flex-col md:flex-row md:items-center justify-between mb-6 border-b border-red-900/30 pb-4 gap-3 relative z-10">
        <h2 className="text-lg font-mono font-bold text-red-500 flex items-center gap-3 tracking-widest">
          <span className="w-2.5 h-2.5 rounded-full bg-red-500 animate-[pulse_1.5s_ease-in-out_infinite] shadow-[0_0_10px_rgba(239,68,68,0.8)]"></span>
          DEV_CONSOLE // IOT
        </h2>
        <span className="text-[10px] font-mono text-red-400/60 border border-red-900/50 px-3 py-1.5 rounded-md bg-red-950/20 backdrop-blur-sm">n8n_WEBHOOK_LINKED</span>
      </div>
      
      {products.length === 0 ? (
        <div className="font-mono text-xs text-red-500/50 py-6 flex flex-col gap-2 relative z-10">
          <span className="opacity-70">&gt; Initializing hardware handshake... OK</span>
          <span className="opacity-70">&gt; Polling weight sensors [0x0F]...</span>
          <span className="text-red-400/80">&gt; Warn: No physical items detected in buffer.</span>
          <span className="animate-pulse text-red-500">&gt; _</span>
        </div>
      ) : (
        <div className="space-y-4 font-mono relative z-10">
          <p className="text-[11px] text-red-400/50 uppercase tracking-widest">&gt; Select virtual item to trigger hardware drop event</p>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            {products.map(p => (
              <div key={p.id} className="flex justify-between items-center bg-red-950/20 p-3 rounded-lg border border-red-900/30 hover:bg-red-950/40 hover:border-red-800/50 transition-all duration-300">
                <span className="text-sm text-red-200/90 truncate mr-2">{p.productName}</span>
                <button 
                  onClick={() => simulateIoTDrop(p.id)}
                  className="bg-red-900/40 hover:bg-red-600 text-white text-[10px] px-3 py-1.5 rounded transition-all uppercase tracking-widest font-bold shadow-[0_0_10px_rgba(220,38,38,0.1)] hover:shadow-[0_0_15px_rgba(220,38,38,0.4)] whitespace-nowrap"
                >
                  Force Drop
                </button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
