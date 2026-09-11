import { useState, useRef } from 'react';

export default function Scanner({ onScanComplete }: { onScanComplete: () => void }) {
  const [scanning, setScanning] = useState(false);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setImageSrc(URL.createObjectURL(file));
    }
  };

  const handleScan = async () => {
    if (!imageSrc) return;
    setScanning(true);
    try {
      await fetch('http://localhost:8080/api/vision/scan', { method: 'POST' });
      onScanComplete();
    } catch (e) {
      console.error(e);
    } finally {
      setScanning(false);
      setImageSrc(null);
    }
  };

  return (
    <div className="glass-card flex flex-col gap-6 relative overflow-hidden group">
      <div className="absolute top-0 right-0 w-48 h-48 bg-blue-500/10 rounded-full blur-3xl -mr-10 -mt-10 transition-opacity group-hover:opacity-100 opacity-50 pointer-events-none"></div>
      
      <div>
        <h2 className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-200 to-cyan-400">Vision AI</h2>
        <p className="text-white/40 text-xs mt-1 uppercase tracking-wider font-semibold">Receipt Scanner</p>
      </div>
      
      <div 
        className="w-full h-56 rounded-2xl border-2 border-dashed border-white/10 hover:border-cyan-400/40 transition-colors relative overflow-hidden bg-black/40 flex items-center justify-center shadow-inner cursor-pointer" 
        onClick={() => !imageSrc && fileInputRef.current?.click()}
      >
        {imageSrc ? (
          <>
            <img src={imageSrc} alt="Receipt" className="w-full h-full object-cover opacity-60 mix-blend-luminosity" />
            {scanning && (
              <div className="absolute left-0 w-full h-3 bg-cyan-400 shadow-[0_0_30px_15px_rgba(34,211,238,0.8)] animate-[scan_2s_ease-in-out_infinite] z-10 mix-blend-screen" />
            )}
            {/* Grid overlay for tech look */}
            <div className="absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.03)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.03)_1px,transparent_1px)] bg-[size:20px_20px] pointer-events-none opacity-50 mix-blend-overlay"></div>
          </>
        ) : (
          <div className="flex flex-col items-center gap-3 text-white/30 group-hover:text-cyan-300/60 transition-colors">
            <svg className="w-12 h-12 drop-shadow-lg" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"></path><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
            <span className="text-sm font-semibold tracking-wide uppercase">Upload Receipt</span>
          </div>
        )}
      </div>

      <input 
        type="file" 
        accept="image/*" 
        className="hidden" 
        ref={fileInputRef} 
        onChange={handleImageUpload} 
      />

      <div className="flex w-full gap-3 mt-auto">
        {imageSrc ? (
          <>
            <button 
              className="glass-btn flex-1 !px-2 bg-red-500/10 border-red-500/20 hover:bg-red-500/20 text-red-200 text-sm" 
              onClick={(e) => { e.stopPropagation(); setImageSrc(null); }} 
              disabled={scanning}
            >
              Clear
            </button>
            <button 
              className="glass-btn flex-[2] bg-blue-500/10 border-blue-400/20 hover:bg-blue-500/30 text-blue-100 hover:text-white shadow-[0_0_20px_rgba(59,130,246,0.1)] hover:shadow-[0_0_20px_rgba(59,130,246,0.2)]" 
              onClick={handleScan} 
              disabled={scanning}
            >
              {scanning ? 'Analyzing...' : 'Process Image'}
            </button>
          </>
        ) : (
          <button className="glass-btn w-full bg-white/5 border-white/5 text-white/30 cursor-not-allowed shadow-none" disabled>
            Awaiting Image
          </button>
        )}
      </div>
    </div>
  );
}
