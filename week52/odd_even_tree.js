function solution(nodes, edges) {
  const n = nodes.length;

  // node 번호 -> index
  const index = new Map();

  for (let i = 0; i < n; i++) {
    index.set(nodes[i], i);
  }

  // degree
  const degree = Array(n).fill(0);

  // union find
  const parent = Array.from({ length: n }, (_, i) => i);

  const find = (x) => {
    while (parent[x] !== x) {
      parent[x] = parent[parent[x]];
      x = parent[x];
    }

    return x;
  };

  const union = (a, b) => {
    a = find(a);
    b = find(b);

    if (a === b) return;

    parent[b] = a;
  };

  // 간선 연결 + degree 계산
  for (const [a, b] of edges) {
    const ai = index.get(a);
    const bi = index.get(b);

    degree[ai]++;
    degree[bi]++;

    union(ai, bi);
  }

  // 각 트리별 same / diff 개수
  // same : node 홀짝 === degree 홀짝
  // diff : node 홀짝 !== degree 홀짝
  const same = new Map();
  const diff = new Map();

  for (let i = 0; i < n; i++) {
    const root = find(i);

    if (!same.has(root)) {
      same.set(root, 0);
      diff.set(root, 0);
    }

    if (nodes[i] % 2 === degree[i] % 2) {
      same.set(root, same.get(root) + 1);
    } else {
      diff.set(root, diff.get(root) + 1);
    }
  }

  let oddEvenTree = 0;
  let reverseOddEvenTree = 0;

  for (const root of same.keys()) {
    // 홀짝 트리:
    // node parity === degree parity인 노드가
    // 정확히 하나여야 그 노드를 root로 잡을 수 있음
    if (same.get(root) === 1) {
      oddEvenTree++;
    }

    // 역홀짝 트리:
    // node parity !== degree parity인 노드가
    // 정확히 하나여야 그 노드를 root로 잡을 수 있음
    if (diff.get(root) === 1) {
      reverseOddEvenTree++;
    }
  }

  return [oddEvenTree, reverseOddEvenTree];
}
