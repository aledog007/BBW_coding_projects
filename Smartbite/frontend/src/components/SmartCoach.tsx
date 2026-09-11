import { useState } from 'react';

type Recipe = {
  title: string;
  savedIngredients: string[];
  steps: string[];
};

export default function SmartCoach() {
  const [recipe, setRecipe] = useState<Recipe | null>(null);
  const [loading, setLoading] = useState(false);

  const getRecipe = async () => {
    setLoading(true);
    try {
      await new Promise(r => setTimeout(r, 600)); // smooth effect
      const res = await fetch('http://localhost:8080/api/recipe/suggest');
      const data = await res.json();
      setRecipe(data);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="glass-card relative overflow-hidden group">
      {/* Background decoration */}
      <div className="absolute -top-24 -right-24 w-56 h-56 bg-amber-500/10 rounded-full blur-3xl transition-opacity opacity-40 group-hover:opacity-100 pointer-events-none"></div>

      <div className="flex items-center justify-between mb-6 relative z-10">
        <div>
          <h2 className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-amber-200 to-orange-400">Smart Coach</h2>
          <p className="text-white/40 text-xs mt-1 uppercase tracking-wider font-semibold">Zero-Waste AI</p>
        </div>
        <div className="w-12 h-12 rounded-2xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center shadow-[0_0_15px_rgba(245,158,11,0.1)]">
          <span className="text-amber-400 text-2xl drop-shadow-md">👨‍🍳</span>
        </div>
      </div>

      <button 
        className="glass-btn w-full flex items-center justify-center gap-2 bg-gradient-to-r from-amber-500/10 to-orange-500/10 border-amber-500/20 hover:border-amber-500/40 text-amber-50 hover:text-white" 
        onClick={getRecipe} 
        disabled={loading}
      >
        {loading ? (
          <span className="animate-pulse tracking-wide">Generating Recipe...</span>
        ) : (
          <>
            <svg className="w-5 h-5 text-amber-400/80" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path></svg>
            <span className="tracking-wide">Suggest Recipe</span>
          </>
        )}
      </button>

      {recipe && (
        <div className="mt-5 animate-fade-in relative z-10">
          <div className="bg-gradient-to-b from-white/[0.08] to-white/[0.02] border border-white/10 p-5 rounded-2xl backdrop-blur-xl shadow-xl">
            <h3 className="text-lg md:text-xl font-bold text-white leading-snug mb-3 drop-shadow-sm">{recipe.title}</h3>
            
            <div className="flex flex-wrap items-center gap-2 mb-4">
              <span className="text-[10px] text-white/40 font-medium uppercase tracking-wider">Uses:</span>
              {recipe.savedIngredients.map(ing => (
                <span key={ing} className="text-[10px] font-bold px-2 py-1 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 shadow-inner">
                  {ing}
                </span>
              ))}
            </div>

            <div className="space-y-3 relative before:absolute before:inset-y-0 before:left-[11px] before:w-[2px] before:bg-white/5 ml-1">
              {recipe.steps.map((step, i) => (
                <div key={i} className="relative pl-8">
                  <div className="absolute left-0 top-0.5 w-6 h-6 rounded-full bg-[#0f172a] border border-amber-500/40 flex items-center justify-center text-[10px] font-black text-amber-300 shadow-[0_0_10px_rgba(245,158,11,0.2)]">
                    {i + 1}
                  </div>
                  <p className="text-sm text-white/70 leading-relaxed pt-0.5">{step}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
